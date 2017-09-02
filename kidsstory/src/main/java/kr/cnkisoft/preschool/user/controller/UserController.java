package kr.cnkisoft.preschool.user.controller;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.user.domain.*;
import kr.cnkisoft.preschool.user.mapper.UserMapper;
import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping(value = "/user/{contact}")//, produces="application/json")
	@ResponseBody
	public LoginUserVo user(@PathVariable String contact) {
		return userService.getLoginUser(contact);
	}
	
	@RequestMapping(value = "/usermsg/{srcId}/{dstId}")//, produces="application/json")
	@ResponseBody
	public List<UserMsgDto> user(@PathVariable int srcId, @PathVariable int dstId) {
		return userMapper.selectListMsg(srcId, dstId);
	}

	@RequestMapping(value="/usermsg/send", method=RequestMethod.POST)
	@ResponseBody
	public int boardProcess(@RequestBody UserMsgDto param) {
		return userMapper.insertMsg(param);
	}
	
	@RequestMapping(value = "/teacher/childList/{contact}")//, produces="application/json")
	@ResponseBody
	public List<StudentVo> chilList(@PathVariable String contact) {
		return userService.getStudentListByTeacherContact(contact);
	}
	
	@RequestMapping(value="/user/reqmedi/create", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVo createReqMedi(@RequestBody ReqMediDto param) {
		return userService.createReqMedi(param);
	}
	
	@RequestMapping(value="/user/reqmedi/list/{teacherId}", method=RequestMethod.GET)
	@ResponseBody
	public List<ReqMediVo> reqMediListByTeacherId(@PathVariable int teacherId) {
		return userService.getListReqMediByTeacherId(teacherId);
	}

	@RequestMapping(value = "user/parent/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResultVo<String> createParent(@RequestBody UserDto parent) {
		userService.createParent(parent);

		CommonResultVo<String> result = new CommonResultVo<>();
		result.setData("success");
		return result;
	}

	@RequestMapping(value = "user/parent/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResultVo<List<UserDto>> createParent() {
		List<UserDto> parentList = userService.getListParentInPreschool();

		CommonResultVo<List<UserDto>> result = new CommonResultVo<>();
		result.setData(parentList);

		return result;
	}

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
}
