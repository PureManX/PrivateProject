package kr.cnkisoft.preschool.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.service.BoardService;
import kr.cnkisoft.preschool.board.vo.BoardLineDto;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.UserVo;

@Controller
public class BoardViewController {
	
	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardService boardService;
	
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
		mav.addObject("lineId", boardService.getCurrentUserBusLineId());
		return mav;
	}

	@RequestMapping(value="/board/teacher/busline/{lineId}")
	public ModelAndView teacherBusLine(@PathVariable Integer lineId) {
		ModelAndView mav = new ModelAndView();
		String viewName = "views/teacher/boarding";
		mav.setViewName(viewName);
		mav.addObject("lineId", lineId);
		return mav;
	}

	@RequestMapping(value="/board/teacher/buslinelist/{type}")
	public ModelAndView teacherBusLineList(@PathVariable String type) {
		ModelAndView mav = new ModelAndView();

		String lineType = "on".equals(type) ? "ATT" : "COM";

		UserVo user = AuthUtils.getLoginUser().getUser();

		String prescoolCode = user.getPreschool().getSchCd();

		List<BoardLineDto> busLineList = boardMapper.selectBoardLineListInfoByLineType(lineType, prescoolCode);

		String viewName = "views/teacher/boardingList";
		mav.setViewName(viewName);
		mav.addObject("lineType", lineType);
		mav.addObject("busLineList", busLineList);
		return mav;
	}
}
