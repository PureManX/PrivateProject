package kr.cnkisoft.kidsstory.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.cnkisoft.kidsstory.board.service.BoardLineService;
import kr.cnkisoft.kidsstory.board.service.BoardLineStationService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentRelationDto;
import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;

@RestController
public class BoardAdminRestController {
	@Autowired
	BoardLineService boardLineService;

	@Autowired
	BoardLineStationService boardLineStationService;

	/**
	 * 운행노선 정보 조회
	 * @return
	 */
	@GetMapping(value="/rest/admin/board/line/list")
	@ResponseBody
	public CommonResultVo adminBoardLineList() {
		List<BoardLineInfoVo> boardLineList = boardLineService.getBoardLineListByCurretLoginPreshcoolCode();

		CommonResultVo result = new CommonResultVo();
		result.setData(boardLineList);

		return result;
	}

	/**
	 * 운행노선 생성
	 * @param boardLineDetail
	 * @return
	 */
	@PostMapping(value="/rest/admin/board/line")
	@ResponseBody
	public CommonResultVo createBoardLine(@RequestBody BoardLineDto boardLine) {
		boardLineService.createBoardLine(boardLine);

		return CommonResultVo.createSuccessResult();
	}

	/**
	 * 운행노선 수정
	 *
	 * @param boardLineDetail
	 * @return
	 */
	@PutMapping(value="/rest/admin/board/line")
	@ResponseBody
	public CommonResultVo modifyBoardLine(@RequestBody BoardLineDto boardLine) {
		boardLineService.modifyBoardLine(boardLine);

		return CommonResultVo.createSuccessResult();
	}

	/**
	 * 정류장 정보 조회
	 * @param lineId
	 * @return
	 */
	@GetMapping(value="/rest/admin/board/station/list/{lineId}")
	@ResponseBody
	public CommonResultVo boardStationList(@PathVariable int lineId) {

		CommonResultVo result = new CommonResultVo();
		result.setData(boardLineStationService.getBoardStationDetailListByLineId(lineId));
		return result;
	}

	/**
	 * 정류장 생성
	 * @param boardLineDetail
	 * @return
	 */
	@PostMapping(value="/rest/admin/board/line/station")
	@ResponseBody
	public CommonResultVo createBoardLineDetail(@RequestBody BoardLineDetailDto boardLineStation) {
		boardLineStationService.createBoardStation(boardLineStation);

		return CommonResultVo.createSuccessResult();
	}

	/**
	 * 정류장 수정
	 *
	 * @param boardLineDetail
	 * @return
	 */
	@PutMapping(value="/rest/admin/board/line/station")
	@ResponseBody
	public CommonResultVo modifyBoardLineDetail(@RequestBody BoardLineDetailDto boardLineStation) {
		boardLineStationService.modifyBoardStation(boardLineStation);

		return CommonResultVo.createSuccessResult();
	}

	/**
	 * 정류장 생성 학생 정보 수정
	 *
	 * @param boardLineDetail
	 * @return
	 */
	@PutMapping(value="/rest/admin/board/line/station/{lineDetailId}/student")
	@ResponseBody
	public CommonResultVo modifyBoardLineStationStudentList(
			@PathVariable("lineDetailId") Integer lineDetailId,
			@RequestBody List<BoardLineStudentRelationDto> studentList) {
		boardLineStationService.modifyBoardListStationStudendList(lineDetailId, studentList);

		return CommonResultVo.createSuccessResult();
	}

}
