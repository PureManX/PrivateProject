package kr.cnkisoft.kidsstory.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.manage.domain.PreschoolNoticeBoardDto;
import kr.cnkisoft.kidsstory.manage.service.NoticeService;
import kr.cnkisoft.kidsstory.user.domain.UserDto;
import kr.cnkisoft.kidsstory.user.service.UserService;

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
