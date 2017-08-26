package kr.cnkisoft.preschool.board.controller;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.vo.BoardLineDto;
import kr.cnkisoft.preschool.board.vo.LineDetailVo;
import kr.cnkisoft.preschool.board.vo.LineHistVo;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController {
	
	@Autowired
	BoardMapper boardMapper;
	
	@RequestMapping(value="/board/list/{lineId}/{histDate}")
	@ResponseBody
	public List<LineDetailVo> boardList(@PathVariable int lineId, @PathVariable String histDate) {
		return boardMapper.selectListLineDetail(lineId, histDate);
	}
	
	@RequestMapping(value="/board/unboadlist/{lineDtlId}")
	@ResponseBody
	public List<LineHistVo> unboardList(@PathVariable int lineDtlId) {
		return boardMapper.selectListLineHist(lineDtlId);
	}
	
	@RequestMapping(value="/board/process", method=RequestMethod.POST)
	@ResponseBody
	public LineHistVo boardProcess(@RequestBody LineHistVo param) {
		boardMapper.insertBoardHist(param);
		
		return param;
	}
	
	@RequestMapping(value="/board/lineinfo/{lineId}")
	@ResponseBody
	public BoardLineDto boardLineInfo(@PathVariable int lineId) {
		return boardMapper.selectBoardLineInfoByLineId(lineId);
	}

	@RequestMapping(value="/board/parent/unboadrequest/{type}")
	public ModelAndView parnetUnboadRequest(@PathVariable String type) {
		ModelAndView mav = new ModelAndView();

		String lineType = "on".equals(type) ? "ATT" : "COM";

		Integer lineDetailId = boardMapper.selectLineDetailIdByLoginParents(lineType, AuthUtils.getLoginUser().getChildren().get(0).getUserId());

		LoginUserVo loginUser = AuthUtils.getLoginUser();
		String viewName = "views/parent/unboarding";
		mav.setViewName(viewName);
		mav.addObject("boardingType", type);
		mav.addObject("lineDetailId", lineDetailId);
		return mav;
	}

	@RequestMapping(value="/board/parent/busline")
	public ModelAndView parnetBusLine() {
		ModelAndView mav = new ModelAndView();
		String viewName = "views/teacher/boarding";
		mav.setViewName(viewName);
		mav.addObject("lineId", 1);
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

		List<BoardLineDto> busLineList = boardMapper.selectBoardLineListInfoByLineType(lineType);

		String viewName = "views/teacher/boardingList";
		mav.setViewName(viewName);
		mav.addObject("lineType", lineType);
		mav.addObject("busLineList", busLineList);
		return mav;
	}

	@RequestMapping(value="/board/linhist/delete/{lineId}")
	@ResponseBody
	public CommonResultVo<String> boardDeleteLineHist(@PathVariable int lineId) {
		boardMapper.deleteLineHistByLineId(lineId);
		return new CommonResultVo<String>();
	}

}
