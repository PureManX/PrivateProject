package kr.cnkisoft.preschool.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.manage.domain.PreschoolNoticeBoardDto;
import kr.cnkisoft.preschool.manage.service.NoticeService;
import kr.cnkisoft.preschool.user.domain.UserDto;
import kr.cnkisoft.preschool.user.service.UserService;

/**
 * Admin Rest 호출 컨트롤러
 * 
 * @author PureMaN
 *
 */
@Controller
public class AdminRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	NoticeService noticeService;
	
	// 학부모 데이터 생성
	@RequestMapping(value = "/rest/admin/parent/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResultVo createParent(@RequestBody UserDto parent) {
		userService.createParent(parent);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");
		return result;
	}
	
	// 학부모 리스트 호출
	@RequestMapping(value = "/rest/admin/parent/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResultVo parentList() {
		List<UserDto> parentList = userService.getListParentInPreschool();

		CommonResultVo result = new CommonResultVo();
		result.setData(parentList);

		return result;
	}
	
	// 공지사항 리스트 조회
	@RequestMapping(value = "/rest/admin/notice/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResultVo noticeList() {
		List<PreschoolNoticeBoardDto> noticeList = noticeService.getNoticeListOfCurrentLoginUser();

		CommonResultVo result = new CommonResultVo();
		result.setData(noticeList);

		return result;
	}
}
