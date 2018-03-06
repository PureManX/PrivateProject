package kr.cnkisoft.kidsstory.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.common.file.domain.FileInfoDto;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.ReqMediDto;
import kr.cnkisoft.kidsstory.user.domain.ReqMediVo;
import kr.cnkisoft.kidsstory.user.domain.StudentVo;
import kr.cnkisoft.kidsstory.user.domain.UserDto;
import kr.cnkisoft.kidsstory.user.domain.UserMsgDto;
import kr.cnkisoft.kidsstory.user.mapper.UserMapper;
import kr.cnkisoft.kidsstory.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping(value = "/user/{contact}")//, produces="application/json")
	public LoginUserVo user(@PathVariable String contact) {
		return userService.getLoginUser(contact);
	}
	
	@RequestMapping(value = "/usermsg/{srcId}/{dstId}")//, produces="application/json")
	public List<UserMsgDto> user(@PathVariable int srcId, @PathVariable int dstId) {
		return userMapper.selectListMsg(srcId, dstId);
	}

	@RequestMapping(value="/usermsg/send", method=RequestMethod.POST)
	public int boardProcess(@RequestBody UserMsgDto param) {
		return userMapper.insertMsg(param);
	}
	
	@RequestMapping(value = "/teacher/childList/{contact}")//, produces="application/json")
	public List<StudentVo> chilList(@PathVariable String contact) {
		return userService.getStudentListByTeacherContact(contact);
	}
	
	@RequestMapping(value="/user/reqmedi/create", method=RequestMethod.POST)
	public CommonResultVo createReqMedi(@RequestBody ReqMediDto param) {
		return userService.createReqMedi(param);
	}
	
	@RequestMapping(value="/user/reqmedi/list/{teacherId}", method=RequestMethod.GET)
	public List<ReqMediVo> reqMediListByTeacherId(@PathVariable int teacherId) {
		return userService.getListReqMediByTeacherId(teacherId);
	}

	@PostMapping({"/rest/user/parent", "/rest/admin/user/parent"})
	public CommonResultVo createParent(@RequestBody UserDto parent) {
		userService.createParent(parent);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");
		return result;
	}

	@PutMapping({"/rest/user/parent", "/rest/admin/user/parent"})
	public CommonResultVo modifyParent(@RequestBody UserDto parent) {
		userService.modifyParnet(parent);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");
		return result;
	}

	@GetMapping( {"/rest/user/parent/list", "/rest/admin/user/parent/list"})
	public CommonResultVo createParent() {
		List<UserDto> parentList = userService.getListParentInPreschool();

		CommonResultVo result = new CommonResultVo();
		result.setData(parentList);

		return result;
	}

	
	@RequestMapping(value = "/rest/user/profileImage", method = RequestMethod.POST)
	public CommonResultVo updateProfileImage (
			@RequestParam MultipartFile file
			, @RequestParam(required=false) Integer userId
			) {
		
		FileInfoDto uploadFile = userService.updateStudentProfileImage(file, userId);
		
		return new CommonResultVo(uploadFile);
	}

	@RequestMapping(value = {"/rest/user/student/list", "/rest/admin/user/student/list"}, method = RequestMethod.GET)
	public CommonResultVo getStudentListInPreschool() {
		List<StudentVo> studentList = userService.getStudentListByCurrentLoginPreshcoolCode();

		CommonResultVo result = new CommonResultVo();
		result.setData(studentList);

		return result;
	}
	
	@PostMapping(value = {"/rest/user/student/", "/rest/admin/user/student/"})
	public CommonResultVo createStudent(@RequestBody StudentVo student) {
		userService.createStudent(student);
		CommonResultVo result = new CommonResultVo();

		return result;
	}
	
	@PutMapping(value = {"/rest/user/student/", "/rest/admin/user/student/"})
	public CommonResultVo modifyStudent(@RequestBody StudentVo student) {
		userService.modifyStudent(student);
		CommonResultVo result = new CommonResultVo();

		return result;
	}
}
