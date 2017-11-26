package kr.cnkisoft.preschool.common.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.push.service.PushService;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	PushService pushService;

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

	@RequestMapping(value="/auth/regist", produces=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String authRegist(
			@RequestParam(value="deviceId", required = false) String deviceId
			, @RequestParam(value="mobileNum", required = false) String mobileNum
			, @RequestParam(value="ostype", required = false) String ostype
		) {

		log.info("deviceId : {} / mobileNum : {} / ostype : {}", deviceId, mobileNum, ostype);

		pushService.registPushId(mobileNum, deviceId, ostype);
		return "OK";
	}

	@RequestMapping(value="/setPhoneNumber")
	public ModelAndView setPhoneNumber() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/common/common");

		return mav;
	}
	
}
