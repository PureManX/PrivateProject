package kr.cnkisoft.kidsstory.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Admin 페이 호출 컨트롤러
 * 
 * @author PureMaN
 *
 */
@Controller
public class AdminViewController {


	// Admin Login 페이지
	@RequestMapping(value= "/admin/login", method = RequestMethod.GET)
	public ModelAndView adminLogin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/login");
		
		return mav;
	}
	
	// Admin Home (미정)
	@RequestMapping(value= "/admin/home", method = RequestMethod.GET)
	public ModelAndView adminHome() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/notice");

		return mav;
	}
	
	// 학생 관리 페이지
	@RequestMapping(value= "/admin/student", method = RequestMethod.GET)
	public ModelAndView adminStudent() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/student");

		return mav;
	}
	
	// 학무보 관리 페이지
	@RequestMapping(value= "/admin/parent", method = RequestMethod.GET)
	public ModelAndView adminParent() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/parent");

		return mav;
	}
	
	// 공지사항 관리 페이지
	@RequestMapping(value= "/admin/notice", method = RequestMethod.GET)
	public ModelAndView adminNotice() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/notice");

		return mav;
	}
	
	// 학급 관리 페이지
	@RequestMapping(value= "/admin/preschool/class", method = RequestMethod.GET)
	public ModelAndView adminPreschoolClass() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/admin/preschool_class");

		return mav;
	}
}
