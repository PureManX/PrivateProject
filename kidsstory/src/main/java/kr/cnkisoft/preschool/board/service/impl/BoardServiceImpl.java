package kr.cnkisoft.preschool.board.service.impl;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.service.BoardService;
import kr.cnkisoft.preschool.board.vo.*;
import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.push.service.PushService;
import kr.cnkisoft.preschool.user.domain.UserVo;
import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void processBoarding(BoardLineHistDto baordLineHist) {
		
		String histDate = DateUtils.currentDateOfYear();

		List<BoardLineDetailDto> boardLineDetailListbefore = boardMapper.selectListNonBoardingListByLineId(baordLineHist.getLineId(), histDate);

		if (boardLineDetailListbefore.isEmpty()) {
			// 에러 처리
		}

		// 남은 노선 첫번째 id와 요청한 id가 같지 않으면 미리 탑성/미탑승 처리
		boolean reservedRequest = !(boardLineDetailListbefore.get(0).getLineDtlId().equals(baordLineHist.getLineDtlId()));

		baordLineHist.setCreatedBy(AuthUtils.getLoginUserId());
		boardMapper.insertBoardHist(baordLineHist);

		List<BoardLineDetailDto> boardLineDetailList = boardMapper.selectListNonBoardingListByLineId(baordLineHist.getLineId(), histDate);

		if (boardLineDetailList.isEmpty()) {
			// 더이상 운행할 노선이 남지 않았으므로 push를 보낼 필요가 없다
			// 종료 버튼을 눌럿을대 전체 도착 푸시 메세지를 알릴 필요가 있는듯?
		} else {
			BoardLineDetailDto nextLocation = boardLineDetailList.get(0);

			// 미리 탑승/미탑승 처리 한 경우가 아닐때에만 다음 노선에 push를 발송한다
			if (!reservedRequest) {
				PreSchoolPushIdDto pushInfo = userService.getPushInfoByLineDetailId(nextLocation.getLineDtlId());

				if (pushInfo != null) {
					String message = PUSH_MESSAGE_START_FROM_PRVIOUS_LOCATION.replace("{location}", nextLocation.getBoardLoc());
					pushService.sendPush(pushInfo.getDeviceId(), message, "http://cnkisoft.cafe24.com/board/parent/busline");
				} else {
					log.warn(" *** Cannot Find FCM Id of User Id: {}", boardLineDetailList.get(0).getStduId());
				}
			}

		}


	}

	@Override
	public void sendPushToAllUsersInBusLine(Integer lineId, String histDate) {
		BoardLineInfoVo lineInfo = getBoardLineInfo(lineId);

		List<BoardLineDetailDto> boardLineDetailDtoList = boardMapper.selectListNonBoardingListByLineId(lineId, histDate);

		for (BoardLineDetailDto boardLineDetail: boardLineDetailDtoList) {
			PreSchoolPushIdDto pushInfo = userService.getPushInfoByLineDetailId(boardLineDetail.getLineDtlId());

			String message = PUSH_MESSAGE_BUS_START.replace("{lineName}", lineInfo.getLine().getLineNm());
			message = message.replace("{bus}", lineInfo.getBus().getBusNum());
			if (pushInfo != null) {
				log.info("send push to parent : {}", pushInfo.getContact());
				pushService.sendPush(pushInfo.getDeviceId(), message, "http://cnkisoft.cafe24.com/board/parent/busline");
			} else {
				log.warn(" *** Cannot Find FCM Id of User Id: {}", boardLineDetail.getStduId());
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
	public void reserveUnboard(BoardLineHistDto boarDLineHist) {
		boarDLineHist.setCreatedBy(AuthUtils.getLoginUserId());
		
		boardMapper.insertBoardHist(boarDLineHist);
		
	}

	@Override
	public void cancelReserverUnboard(String lineHistId) {
		boardMapper.deleteBoardHist(lineHistId);
	}

	@Override
	public BoardLineServiceDto getBoardService(Integer lineId) {
		return boardMapper.selectStartedBoardService(lineId);
	}
}
