package kr.cnkisoft.kidsstory.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;
import kr.cnkisoft.kidsstory.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserViewController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value= "/user/chat", method = RequestMethod.GET)
	public ModelAndView chat(@RequestParam(required = true) Integer targetId) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/common/chat");

		LoginUserVo loginUser = AuthUtils.getLoginUser();

		UserVo sourceUser = loginUser.getUser();
		UserVo targetUser = userService.getUser(targetId);

		log.info(sourceUser.toString());
		log.info(targetUser.toString());

		mav.addObject("sourceUser", sourceUser);
		mav.addObject("targetUser", targetUser);

		return mav;
	}
	
	@RequestMapping(value= "/user/gallery/{classId}", method = RequestMethod.GET)
	public ModelAndView userGallery(
			@PathVariable(value = "classId", required = true)Integer classId) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/common/gallery");
		
		mav.addObject("imageList", userService.getDailyImageListOfClass(classId));

		return mav;
	}
}
