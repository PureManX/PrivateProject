package kr.cnkisoft.preschool.board.service.impl;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.service.BoardService;
import kr.cnkisoft.preschool.board.vo.*;
import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.push.service.PushService;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.domain.UserVo;
import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	static final String PUSH_MESSAGE_BUS_START = "<{lineName}>노선 <{bus}>버스가 유치원에서 출발하였습니다.";
	static final String PUSH_MESSAGE_START_FROM_PRVIOUS_LOCATION = "버스가 다음 목적지로<{location}> 출발하였습니다. 승차 준비해주세요.";

	@Autowired
	BoardMapper boardMapper;

	@Autowired
	UserService userService;

	@Autowired
	PushService pushService;

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
		BoardLineInfoVo lineInfo = getBoardLineInfo(lineId);

		List<BoardLineDetailDto> boardLineDetailDtoList = boardMapper.selectListNonBoardingListByLineId(lineId, histDate);

		for (BoardLineDetailDto boardLineDetail: boardLineDetailDtoList) {
			List<StudentVo> stationStudentList = userService.getStudentListByBoardLineDetailId(boardLineDetail.getLineDtlId());

			for (StudentVo student : stationStudentList) {
				// 학생의 모든 부모에게 푸시 발송
				
				for (ParentVo parent : student.getParents()) {
					PreSchoolPushIdDto pushInfo = parent.getPushInfo();
					
					if (pushInfo != null && !StringUtils.isEmpty(pushInfo.getDeviceId())) {
						String message = PUSH_MESSAGE_BUS_START.replace("{lineName}", lineInfo.getLine().getLineNm());
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
	public BoardLineInfoVo getBoardLineInfo(Integer lineId) {
		// 노선 정보 조회
		BoardLineDto line = boardMapper.selectBoardLine(lineId);

		// 버스 정보 조회
		PreschoolBusDto bus = boardMapper.selectPreschoolBus(line.getBusId());

		return BoardLineInfoVo.builder().bus(bus).line(line).build();
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
	public BoardLineServiceDto getBoardService(Integer lineId) {
		return boardMapper.selectStartedBoardService(lineId);
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
