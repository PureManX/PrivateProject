package kr.cnkisoft.preschool.board.controller;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.board.mapper.BoardMapper;
import kr.cnkisoft.preschool.board.service.BoardService;
import kr.cnkisoft.preschool.board.vo.BoardLineDto;
import kr.cnkisoft.preschool.board.vo.LineDetailVo;
import kr.cnkisoft.preschool.board.vo.BoardLineHistDto;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController {
	
	@Autowired
	BoardMapper boardMapper;

	@Autowired
	BoardService boardService;
	
	@RequestMapping(value="/board/list/{lineId}/{histDate}")
	@ResponseBody
	public List<LineDetailVo> boardList(@PathVariable int lineId, @PathVariable String histDate) {
		return boardMapper.selectListLineDetail(lineId, histDate);
	}
	
	@RequestMapping(value="/board/unboadlist/{lineDtlId}")
	@ResponseBody
	public List<BoardLineHistDto> unboardList(@PathVariable int lineDtlId) {
		return boardMapper.selectListLineHist(lineDtlId);
	}
	
	@RequestMapping(value="/board/process", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo boardProcess(@RequestBody BoardLineHistDto boardLineHist) {
		boardService.processBoarding(boardLineHist);
		
		return new CommonResultVo();
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

		Integer lineDetailId = boardMapper.selectLineDetailIdByLoginParents(lineType, ((ParentVo)(AuthUtils.getLoginUser().getUser())).getChildren().get(0).getUserId());

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

	@RequestMapping(value="/board/linhist/delete/{lineId}")
	@ResponseBody
	public CommonResultVo<String> boardDeleteLineHist(@PathVariable int lineId) {
		boardMapper.deleteLineHistByLineId(lineId);
		return new CommonResultVo<String>();
	}

	@RequestMapping(value="/board/line/start/{lineId}")
	@ResponseBody
	public CommonResultVo<String> startBoardService(@PathVariable Integer lineId) {
		boardService.startBoardService(lineId);
		return new CommonResultVo<String>();
	}
}
