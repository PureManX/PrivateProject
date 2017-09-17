package kr.cnkisoft.preschool.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		mav.addObject("menuList", menuService.getMenuListOfCurrentLoginUser());
		return mav;
	}
}
