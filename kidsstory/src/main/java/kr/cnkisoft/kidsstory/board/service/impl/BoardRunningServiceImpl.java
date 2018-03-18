package kr.cnkisoft.kidsstory.board.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.kidsstory.board.mapper.BoardLineStationMapper;
import kr.cnkisoft.kidsstory.board.mapper.BoardMapper;
import kr.cnkisoft.kidsstory.board.service.BoardRunningService;
import kr.cnkisoft.kidsstory.board.service.BoardLineService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineIndivualInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineWithDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardProcessParamVo;
import kr.cnkisoft.kidsstory.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.kidsstory.push.service.PushService;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.ParentVo;
import kr.cnkisoft.kidsstory.user.domain.StudentVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;
import kr.cnkisoft.kidsstory.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Service
@Slf4j
public class BoardRunningServiceImpl implements BoardRunningService {

	static final String PUSH_MESSAGE_BUS_START = "<{lineName}>노선 <{bus}>버스가 유치원에서 출발하였습니다.";
	static final String PUSH_MESSAGE_BUS_END = "<{lineName}>노선 <{bus}>버스가 유치원에 도착하였습니다.";
	static final String PUSH_MESSAGE_START_FROM_PRVIOUS_LOCATION = "버스가 다음 목적지로<{location}> 출발하였습니다. 승차 준비해주세요.";

	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardLineStationMapper boardLineStationMapper;

	@Autowired
	UserService userService;

	@Autowired
	PushService pushService;

	@Autowired
	BoardLineService boardLineService;

	@Override
	public void processBoarding(BoardProcessParamVo boardProcessParam) {

		String histDate = DateUtils.currentDateOfYear();

		// 노선 대상 리스트 추출
		List<BoardLineDetailDto> boardLineDetailListbefore = boardMapper.selectListNonBoardingListByLineId(boardProcessParam.getLineId(), histDate);

		if (boardLineDetailListbefore.isEmpty()) {
			// 에러 처리
		}

		// 남은 노선 첫번째 id와 요청한 id가 같지 않으면 미리 탑성/미탑승 처리
//		boolean reservedRequest = !(boardLineDetailListbefore.get(0).getLineDtlId().equals(baordLineHist.getLineDtlId()));

		// 노선 운행 이력 생성
		createBoardDeatilHist(boardProcessParam);

		// 학생 탑승,미탑승 처리
		createBoardDetailStudentHist(boardProcessParam);

		List<BoardLineDetailDto> boardLineDetailList = boardMapper.selectListNonBoardingListByLineId(boardProcessParam.getLineId(), histDate);

		if (boardLineDetailList.isEmpty()) {
			// 더이상 운행할 노선이 남지 않았으므로 push를 보낼 필요가 없다
			// 종료 버튼을 눌럿을대 전체 도착 푸시 메세지를 알릴 필요가 있는듯?
		} else {
			// 다음 정차역의 학생 대상 리스트
			BoardLineDetailDto nextLocation = boardLineDetailList.get(0);
			List<StudentVo> stationStudentList = userService.getStudentListByBoardLineDetailId(nextLocation.getLineDtlId());

			for (StudentVo student : stationStudentList) {
				// 학생의 모든 부모에게 푸시 발송

				for (ParentVo parent : student.getParents()) {
					PreSchoolPushIdDto pushInfo = parent.getPushInfo();

					if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getDeviceId())) {
						String message = PUSH_MESSAGE_START_FROM_PRVIOUS_LOCATION.replace("{location}", nextLocation.getBoardLoc());
						log.info("푸시 발송 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
						pushService.sendPush(pushInfo.getDeviceId(), message, "http://cnkisoft.cafe24.com/board/parent/busline");
					} else {
						log.warn("푸시 발송 정보 미존재 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
					}

				}
			}

		}
	}

	@Override
	public void sendPushToAllUsersInBusLine(Integer lineId, String histDate) {
		BoardLineInfoVo lineInfo = boardLineService.getBoardLineBasicInfo(lineId);

		List<BoardLineDetailDto> boardLineDetailDtoList = boardMapper.selectListNonBoardingListByLineId(lineId, histDate);

		for (BoardLineDetailDto boardLineDetail: boardLineDetailDtoList) {
			List<StudentVo> stationStudentList = userService.getStudentListByBoardLineDetailId(boardLineDetail.getLineDtlId());

			for (StudentVo student : stationStudentList) {
				// 학생의 모든 부모에게 푸시 발송

				for (ParentVo parent : student.getParents()) {
					PreSchoolPushIdDto pushInfo = parent.getPushInfo();

					if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getDeviceId())) {
						String message = PUSH_MESSAGE_BUS_START.replace("{lineName}", lineInfo.getLineNm());
						message = message.replace("{bus}", lineInfo.getBus().getBusNum());
						log.info("푸시 발송 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
						pushService.sendPush(pushInfo.getDeviceId(), message, "http://cnkisoft.cafe24.com/board/parent/busline");
					} else {
						log.warn("푸시 발송 정보 미존재 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
					}

				}
			}
		}
	}

	@Override
	public void startBoardService(Integer lineId) {
		Integer serviceTeacherId = AuthUtils.getLoginUserId();

		BoardLineServiceDto boardLineService = new BoardLineServiceDto();
		boardLineService.setLineId(lineId);
		boardLineService.setServiceStartDt(new Date());
		boardLineService.setServiceTeacherId(serviceTeacherId);
		boardLineService.setCreatedBy(serviceTeacherId);

		// 운행 서비스 기록 추가
		boardMapper.insertBoardService(boardLineService);

		// 대상 부모 전체에 푸시 메세지 전달

		sendPushToAllUsersInBusLine(lineId, DateUtils.currentDateOfYear());
	}


	@Override
	public void endBoardService(Integer lineId) {
//		BoardLineServiceDto inProgressBoardLineService = boardMapper.seelctInProgressBoardServiceByLineId(lineId);
		BoardLineInfoVo boardLineInfo = boardLineService.getInProgressBoardLineInfoByLineId(lineId);

		// validation 처리
		if (!boardLineInfo.isBoardLineStarted()) {
			// 출발하지 않았을 경우
		}

		// 출발 했으나 모두 도착하지 않았을때 만료 처리할 경우
		boardMapper.updateBoardServiceEndDate(boardLineInfo.getService().getLineServiceId());

		sendPushToBoardCompleteUsersInBusLine(lineId, DateUtils.currentDateOfYear());
	}


	public void sendPushToBoardCompleteUsersInBusLine(Integer lineId, String histDate) {
		BoardLineInfoVo lineInfo = boardLineService.getBoardLineBasicInfo(lineId);

		List<BoardLineDetailDto> boardLineDetailDtoList = boardMapper.selectListBoardingCompleteListByLineId(lineId, histDate);

		for (BoardLineDetailDto boardLineDetail: boardLineDetailDtoList) {
			List<StudentVo> stationStudentList = userService.getStudentListByBoardLineDetailId(boardLineDetail.getLineDtlId());

			for (StudentVo student : stationStudentList) {
				// 학생의 모든 부모에게 푸시 발송

				for (ParentVo parent : student.getParents()) {
					PreSchoolPushIdDto pushInfo = parent.getPushInfo();

					if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getDeviceId())) {
						String message = PUSH_MESSAGE_BUS_END.replace("{lineName}", lineInfo.getLineNm());
						message = message.replace("{bus}", lineInfo.getBus().getBusNum());
						log.info("푸시 발송 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
						pushService.sendPush(pushInfo.getDeviceId(), message, "http://cnkisoft.cafe24.com/board/parent/busline");
					} else {
						log.warn("푸시 발송 정보 미존재 [학생 ID : {}, 학생 : {}, 부모 : {}, 전화번호 : {}]", student.getUserId(), student.getUserNm(), parent.getUserNm(), parent.getContact());
					}

				}
			}
		}
	}

	@Override
	public Integer getCurrentUserBusLineId() {
		UserVo child = AuthUtils.getLoginUser().getChildren().get(0);

		List<Integer> lineIdList = boardMapper.selectLineIdInBoardService(child.getUserId());

		if (lineIdList.isEmpty()) {
			return null;
		} else {
			return lineIdList.get(0);
		}
	}



	@Override
	public void reserveUnboard(BoardLineStudentHistDto boarDLineHist) {
		boarDLineHist.setCreatedBy(AuthUtils.getLoginUserId());

		boardMapper.insertBoardDetailStudentHist(boarDLineHist);

	}

	@Override
	public void cancelReserverUnboard(Integer lineHistId) {
		boardMapper.deleteBoardHist(lineHistId);
	}

	@Override
	public List<BoardLineDetailVo> getBoardLineDetailList(Integer lineId, String histDate) {
		return boardMapper.selectListLineDetail(lineId, histDate);
	}

	private void createBoardDeatilHist(BoardProcessParamVo boardProcessParam) {
		BoardLineDetailHistDto param = new BoardLineDetailHistDto();
		param.setCreatedBy(AuthUtils.getLoginUserId());
		param.setLineDtlId(boardProcessParam.getLineDetailId());
		param.setHistDate(boardProcessParam.getHistDate());

		boardMapper.insertBoardDetailHist(param);
	}

	private void createBoardDetailStudentHist(BoardProcessParamVo boardProcessParam) {
		List<BoardLineStudentHistDto> dtoList = boardProcessParam.getProcessList();

		for (BoardLineStudentHistDto boardLineStudentHist : dtoList) {
			boardLineStudentHist.setCreatedBy(AuthUtils.getLoginUserId());
			boardLineStudentHist.setUpdatedBy(AuthUtils.getLoginUserId());
			boardMapper.insertBoardDetailStudentHist(boardLineStudentHist);
		}
	}

	@Override
	public BoardLineIndivualInfoVo getBoardLineWithDetailByStudentId(Integer studentId) {
		BoardLineWithDetailVo attLineDetail = boardMapper.selectAttBoardLineDetailByStudentId(studentId);
		BoardLineWithDetailVo comLineDetail = boardMapper.selectComBoardLineDetailByStudentId(studentId);

		return BoardLineIndivualInfoVo.builder()
				.attDetail(attLineDetail)
				.comDetail(comLineDetail)
				.build()
				;
	}
}
