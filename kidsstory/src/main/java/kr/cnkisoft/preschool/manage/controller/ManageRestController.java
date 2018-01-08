package kr.cnkisoft.preschool.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.manage.domain.PreschoolMenuDto;
import kr.cnkisoft.preschool.manage.domain.PreschoolNoticeBoardDto;
import kr.cnkisoft.preschool.manage.service.MenuService;
import kr.cnkisoft.preschool.manage.service.NoticeService;

@RestController
public class ManageRestController {

	@Autowired
	NoticeService noticeService; 
	
	@Autowired
	MenuService menuService;
	
	@GetMapping(value={"/rest/diary/{diaryId}", "/rest/notice/{diaryId}"})
	public CommonResultVo getDairy(
			@PathVariable("diaryId")Integer diaryId) {
		CommonResultVo result = new CommonResultVo();
		result.setData(noticeService.getNoticeItemByNoticeId(diaryId));
		return result;
	}
	
	@PostMapping(value={"/rest/diary/create", "/rest/notice/create"})
	public CommonResultVo createDairy(
			@RequestBody PreschoolNoticeBoardDto noticeBoard) {
		CommonResultVo result = new CommonResultVo();
		noticeService.createNoticeItem(noticeBoard);
		
		return result;
	}

	@PutMapping(value={"/rest/diary/update", "/rest/notice/update"})
	public CommonResultVo noticeList(
			@RequestBody PreschoolNoticeBoardDto noticeBoard) {
		CommonResultVo result = new CommonResultVo();
		noticeService.modifyNoticeItem(noticeBoard);
		
		return result;
	}
	
	@DeleteMapping(value={"/rest/diary/{diaryId}", "/rest/notice/{diaryId}"})
	public CommonResultVo deleteDairy(
			@PathVariable("diaryId")Integer diaryId) {
		CommonResultVo result = new CommonResultVo();
		noticeService.removeNoticeItem(diaryId);
		
		return result;
	}
	

	@PostMapping(value={"/rest/menu/create"})
	public CommonResultVo createMenu(
			@RequestBody PreschoolMenuDto preschoolMenu) {
		CommonResultVo result = new CommonResultVo();
		menuService.createMenuItem(preschoolMenu);
		
		return result;
	}

	@DeleteMapping(value={"/rest/menu/{menuId}"})
	public CommonResultVo deleteMenu(
			@PathVariable("menuId")Integer menuId) {
		CommonResultVo result = new CommonResultVo();
		menuService.removeMenuItem(menuId);
		
		return result;
	}
}
