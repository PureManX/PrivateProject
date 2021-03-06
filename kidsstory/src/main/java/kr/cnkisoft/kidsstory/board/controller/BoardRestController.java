package kr.cnkisoft.kidsstory.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.cnkisoft.kidsstory.board.mapper.BoardMapper;
import kr.cnkisoft.kidsstory.board.service.BoardRunningService;
import kr.cnkisoft.kidsstory.board.service.BoardLineService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardProcessParamVo;
import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;

@Controller
public class BoardRestController {

	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardLineService boardLineService;

	@Autowired
	BoardRunningService boardLineStationService;


	@RequestMapping(value="/rest/board/list/{lineId}/{histDate}", method=RequestMethod.GET)
	@ResponseBody
	public List<BoardLineDetailVo> boardList(@PathVariable int lineId, @PathVariable String histDate) {
		// 서비스 추가 및 쿼리 변경 필요
		return boardLineStationService.getBoardLineDetailList(lineId, histDate);
	}


	/**
	 * 운행 처리
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/process", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo boardProcess(@RequestBody BoardProcessParamVo boardLineHist) {
		boardLineStationService.processBoarding(boardLineHist);

		return new CommonResultVo();
	}

	/**
	 * 학부모 미탑승 예약 기록 조회
	 * @param lineDtlId
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve/{lineDtlId}/{stduentId}", method=RequestMethod.GET)
	@ResponseBody
	public List<BoardLineStudentHistDto> unboardList(@PathVariable Integer lineDtlId, @PathVariable Integer stduentId) {
		return boardMapper.selectListLineHist(lineDtlId ,stduentId);
	}


	/**
	 * 학부모 미탑승 예약 등록
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo unboardReserve(@RequestBody BoardLineStudentHistDto boardLineHist) {
		boardLineStationService.reserveUnboard(boardLineHist);

		return new CommonResultVo();
	}

	/**
	 * 학부모 미탑승 예약 취소
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve/{lineHistId}", method=RequestMethod.DELETE)
	@ResponseBody
	public CommonResultVo unboardReserve(@PathVariable("lineHistId")Integer lineHistId) {
		boardLineStationService.cancelReserverUnboard(lineHistId);

		return new CommonResultVo();
	}


	@RequestMapping(value="/rest/board/lineinfo/{lineId}")
	@ResponseBody
	public BoardLineInfoVo boardLineInfo(@PathVariable int lineId) {
		return boardLineService.getInProgressBoardLineInfoByLineId(lineId);
	}

	/**
	 * 선생님 : 노선 운행 시작
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo startBoardService(@PathVariable Integer lineId) {
		boardLineStationService.startBoardService(lineId);
		return new CommonResultVo();
	}

	/**
	 * 선생님 : 노선 운행 종료
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.PUT)
	@ResponseBody
	public CommonResultVo endBoardService(@PathVariable Integer lineId) {
		boardLineStationService.endBoardService(lineId);
		return new CommonResultVo();
	}

	/**
	 * 선생님 : 운행 정보 조회
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.GET)
	@ResponseBody
	public CommonResultVo getBoardService(@PathVariable Integer lineId) {
		BoardLineInfoVo boardLineInfoVo = boardLineService.getInProgressBoardLineInfoByLineId(lineId);
		CommonResultVo result = new CommonResultVo();
		result.setData(boardLineInfoVo);
		return result;
	}

	/**
	 * 테스트 코드 라인 기록 삭제
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}/{histDate}", method=RequestMethod.DELETE)
	@ResponseBody
	public CommonResultVo boardDeleteLineHist(@PathVariable Integer lineId, @PathVariable String histDate) {
		boardMapper.deleteLineDetailHistByLineId(lineId, histDate);
		boardMapper.deleteLineDetailStudentHistByLineId(lineId, histDate);
		boardMapper.deleteBoardService(lineId);
		return new CommonResultVo();
	}


	/**
	 * 학생별 상하차 노선 정보 조회
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/line/detail/{studentId}", method=RequestMethod.GET)
	@ResponseBody
	public CommonResultVo boardLineDetail(@PathVariable("studentId") Integer studentId) {
		CommonResultVo result = new CommonResultVo();
		result.setData(boardLineStationService.getBoardLineWithDetailByStudentId(studentId));
		return result;
	}
}
