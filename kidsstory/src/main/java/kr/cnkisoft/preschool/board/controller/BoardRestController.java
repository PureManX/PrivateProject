package kr.cnkisoft.preschool.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.service.BoardService;
import kr.cnkisoft.preschool.board.vo.BoardLineDto;
import kr.cnkisoft.preschool.board.vo.BoardLineHistDto;
import kr.cnkisoft.preschool.board.vo.BoardLineServiceDto;
import kr.cnkisoft.preschool.board.vo.LineDetailVo;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;

@Controller
public class BoardRestController {
	
	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/board/list/{lineId}/{histDate}", method=RequestMethod.GET)
	@ResponseBody
	public List<LineDetailVo> boardList(@PathVariable int lineId, @PathVariable String histDate) {
		// 서비스 추가 및 쿼리 변경 필요
		return boardMapper.selectListLineDetail(lineId, histDate);
	}
	
	/**
	 * 운행 처리
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/process", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo boardProcess(@RequestBody BoardLineHistDto boardLineHist) {
		boardService.processBoarding(boardLineHist);
		
		return new CommonResultVo();
	}

	/**
	 * 학부모 미탑승 예약 기록 조회 
	 * @param lineDtlId
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve/{lineDtlId}", method=RequestMethod.GET)
	@ResponseBody
	public List<BoardLineHistDto> unboardList(@PathVariable int lineDtlId) {
		return boardMapper.selectListLineHist(lineDtlId);
	}

	
	/**
	 * 학부모 미탑승 예약 등록 
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo unboardReserve(@RequestBody BoardLineHistDto boardLineHist) {
		boardService.reserveUnboard(boardLineHist);
		
		return new CommonResultVo();
	}

	/**
	 * 학부모 미탑승 예약 취소 
	 * @param boardLineHist
	 * @return
	 */
	@RequestMapping(value="/rest/board/unboard/reserve/{lineHistId}", method=RequestMethod.DELETE)
	@ResponseBody
	public CommonResultVo unboardReserve(@PathVariable("lineHistId") String lineHistId) {
		boardService.cancelReserverUnboard(lineHistId);
		
		return new CommonResultVo();
	}

	
	@RequestMapping(value="/rest/board/lineinfo/{lineId}")
	@ResponseBody
	public BoardLineDto boardLineInfo(@PathVariable int lineId) {
		return boardMapper.selectBoardLineInfoByLineId(lineId);
	}

	/**
	 * 선생님 : 노선 운행 시작
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo<String> startBoardService(@PathVariable Integer lineId) {
		boardService.startBoardService(lineId);
		return new CommonResultVo<String>();
	}
	
	/**
	 * 선생님 : 운행 정보 조회
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.GET)
	@ResponseBody
	public CommonResultVo<BoardLineServiceDto> getBoardService(@PathVariable Integer lineId) {
		BoardLineServiceDto boardLineService = boardService.getBoardService(lineId);
		CommonResultVo<BoardLineServiceDto> result = new CommonResultVo<BoardLineServiceDto>();
		result.setData(boardLineService);
		return result;
	}

	/**
	 * 테스트 코드 라인 기록 삭제
	 * @param lineId
	 * @return
	 */
	@RequestMapping(value="/rest/board/service/{lineId}", method=RequestMethod.DELETE)
	@ResponseBody
	public CommonResultVo<String> boardDeleteLineHist(@PathVariable int lineId) {
		boardMapper.deleteLineHistByLineId(lineId);
		boardMapper.deleteBoardService(lineId);
		return new CommonResultVo<String>();
	}
}
