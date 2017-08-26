package kr.cnkisoft.preschool.common.controller;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value="/test/{path}/{page}")
	public ModelAndView test3(@PathVariable String path, @PathVariable String page) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/" +  path + "/" + page);
		
		return mav;
	}
	
	@RequestMapping(value="/")
	public ModelAndView main(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="mobileNum", required = false) String mobileNum) {

		ModelAndView mav = new ModelAndView();

		LoginUserVo loginUser = AuthUtils.getLoginUser();

		String viewName = "views/main/main_guest";

		switch (loginUser.getUserType()) {
			case GUEST:
				viewName = "views/main/main_guest";
				break;
			case PARENT:
				viewName = "views/main/main_parents";
				break;
			case TEACHER:
				viewName = "views/main/main_teacher";
				break;
			default:
				break;
		}
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value="/auth/request")
	public String authRequest() {
		return "views/auth/request";
	}

	@RequestMapping(value="/auth/processing")
	public String authRequest(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="mobileNum", required = true) String mobileNum) {

		Cookie authCookie = new Cookie("KidsStory", mobileNum);
		authCookie.setMaxAge(-1);
		authCookie.setPath("/");

		response.addCookie(authCookie);
		return "redirect:/";
	}

	@RequestMapping(value="/guest/introduce")
	public ModelAndView guestIntroduce() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/guest/introduce");

		return mav;
	}

}
