package kr.cnkisoft.kidsstory.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.manage.domain.PreschoolNoticeBoardDto;
import kr.cnkisoft.kidsstory.manage.mapper.NoticeMapper;
import kr.cnkisoft.kidsstory.manage.service.NoticeService;
import kr.cnkisoft.kidsstory.user.domain.UserVo;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeMapper noticeMapper; 
	

	@Override
	public List<PreschoolNoticeBoardDto> getNoticeListOfCurrentLoginUser() {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd(); 
		return getNoticeList(preschoolCode, "NOTICE");
	}

	@Override
	public List<PreschoolNoticeBoardDto> getDiaryListOfCurrentLoginUser() {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd();
		return getNoticeList(preschoolCode, "DIARY");
	}

	List<PreschoolNoticeBoardDto> getNoticeList(String preschoolCode, String noticeType) {
		return noticeMapper.selectListNotice(preschoolCode, noticeType); 
	}

	@Override
	public void createNoticeItem(PreschoolNoticeBoardDto preschoolNoticeBoard) {
		UserVo loginUser = AuthUtils.getLoginUser().getUser();
		String preschoolCode = loginUser.getPreschool().getSchCd();
		Integer loginUserId = loginUser.getUserId();
		
		String convertedCrToBrtag = preschoolNoticeBoard.getNoticeContent().replaceAll("\n", "<br>");
		preschoolNoticeBoard.setNoticeContent(convertedCrToBrtag);
		preschoolNoticeBoard.setCreatedBy(loginUserId);
		preschoolNoticeBoard.setSchCd(preschoolCode);
		
		noticeMapper.insertNotice(preschoolNoticeBoard);
	}

	@Override
	public void modifyNoticeItem(PreschoolNoticeBoardDto preschoolNoticeBoard) {
		UserVo loginUser = AuthUtils.getLoginUser().getUser();
		String preschoolCode = loginUser.getPreschool().getSchCd();
		Integer loginUserId = loginUser.getUserId();
		
		String convertedCrToBrtag = preschoolNoticeBoard.getNoticeContent().replaceAll("\n", "<br>");
		preschoolNoticeBoard.setNoticeContent(convertedCrToBrtag);
		preschoolNoticeBoard.setUpdatedBy(loginUserId);
		preschoolNoticeBoard.setSchCd(preschoolCode);
		
		noticeMapper.updateNoticeByNoticeId(preschoolNoticeBoard);
	}

	@Override
	public void removeNoticeItem(Integer noticeId) {
		noticeMapper.deleteNoticeByNoticeId(noticeId);
	}

	@Override
	public PreschoolNoticeBoardDto getNoticeItemByNoticeId(Integer noticeId) {
		return noticeMapper.selectNoticeByNoticeId(noticeId);
	}

}
