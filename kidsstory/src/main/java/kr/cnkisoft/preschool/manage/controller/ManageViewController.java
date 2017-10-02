package kr.cnkisoft.preschool.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.preschool.manage.service.MenuService;
import kr.cnkisoft.preschool.manage.service.NoticeService;

@Controller
public class ManageViewController {

	@Autowired
	NoticeService noticeService; 
	
	@Autowired
	MenuService menuService;
	
	@RequestMapping(value="/notice/list")
	public ModelAndView noticeList() {
		ModelAndView mav = new ModelAndView();

		String viewName = "views/manage/noticeList";
		mav.setViewName(viewName);
		mav.addObject("noticeList", noticeService.getNoticeListOfCurrentLoginUser());
		return mav;
	}
	
	@RequestMapping(value="/menu/list")
	public ModelAndView menuList() {
		ModelAndView mav = new ModelAndView();

		String viewName = "views/manage/menuList";
		mav.setViewName(viewName);
		mav.addObject("menuList", menuService.getWekklyMenuListOfCurrentLoginUser());
		return mav;
	}

	@RequestMapping(value="/menu/detail")
	public ModelAndView menuDetail(
			@RequestParam(value = "menuDate", required = false) String menuDate
			) {
		if (StringUtils.isEmpty(menuDate)) {
			menuDate = DateUtils.currentDateOfYear();
		}
		
		ModelAndView mav = new ModelAndView();

		String viewName = "views/manage/menuDetail";
		mav.setViewName(viewName);
		mav.addObject("menuList", menuService.getDailyMenuListOfCurrentLoginUser(menuDate));
		return mav;
	}

	@RequestMapping(value="/menu/today")
	public ModelAndView menuToday(
			@RequestParam(value = "menuDate", required = false) String menuDate
			) {
		if (StringUtils.isEmpty(menuDate)) {
			menuDate = DateUtils.currentDateOfYear();
		}
		
		ModelAndView mav = new ModelAndView();

		String viewName = "views/manage/menuDetail";
		mav.setViewName(viewName);
		mav.addObject("menuList", menuService.getDailyMenuListOfCurrentLoginUser(menuDate));
		return mav;
	}
	
	@RequestMapping(value="/diary/list")
	public ModelAndView diaryList() {
		ModelAndView mav = new ModelAndView();

		String viewName = "views/manage/diaryList";
		mav.setViewName(viewName);
		mav.addObject("noticeList", noticeService.getDiaryListOfCurrentLoginUser());
		return mav;
	}

}
