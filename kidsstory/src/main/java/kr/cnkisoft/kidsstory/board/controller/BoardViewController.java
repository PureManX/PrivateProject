package kr.cnkisoft.kidsstory.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.cnkisoft.kidsstory.board.mapper.BoardMapper;
import kr.cnkisoft.kidsstory.board.service.BoardLineDetailService;
import kr.cnkisoft.kidsstory.board.service.BoardLineService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;

@Controller
public class BoardViewController {
	
	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardLineDetailService boardLineDetailService;
	
	@Autowired
	BoardLineService boardLineService;
	
	@RequestMapping(value="/board/parent/unboadrequest")
	public ModelAndView parnetUnboadRequest(
			@RequestParam("type") String type
			, @RequestParam("studendId") Integer studendId
			) {
		ModelAndView mav = new ModelAndView();

		String lineType = "on".equals(type) ? "ATT" : "COM";

		Integer lineDetailId = boardMapper.selectLineDetailIdByLoginParents(lineType, studendId);

		String viewName = "views/parent/unboarding";
		mav.setViewName(viewName);
		mav.addObject("boardingType", type);
		mav.addObject("lineDetailId", lineDetailId);
		mav.addObject("studendId", studendId);
		return mav;
	}

	@RequestMapping(value="/board/parent/busline")
	public ModelAndView parnetBusLine() {
		ModelAndView mav = new ModelAndView();
		String viewName = "views/teacher/boarding";
		mav.setViewName(viewName);
		mav.addObject("lineId", boardLineDetailService.getCurrentUserBusLineId());
		return mav;
	}

	@RequestMapping(value="/board/teacher/busline/{lineId}")
	public ModelAndView teacherBusLine(@PathVariable Integer lineId) {
		ModelAndView mav = new ModelAndView();
		String viewName = "views/teacher/boarding";
		BoardLineInfoVo boardLineInfo = boardLineService.getInProgressBoardLineInfoByLineId(lineId);
		
		mav.setViewName(viewName);
//		mav.addObject("lineId", lineId);
		mav.addObject("boardLineInfo", boardLineInfo);
		return mav;
	}

	/**
	 * 유치원 버스 노선 정보 조회
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/board/teacher/buslinelist/{type}")
	public ModelAndView teacherBusLineList(@PathVariable String type) {
		ModelAndView mav = new ModelAndView();

		String lineType = "on".equals(type) ? "ATT" : "COM";
		
		List<BoardLineInfoVo> busLineList = boardLineService.getBoardLineList(lineType);

		String viewName = "views/teacher/boardingList";
		mav.setViewName(viewName);
		mav.addObject("lineType", lineType);
		mav.addObject("busLineList", busLineList);
		return mav;
	}
}
