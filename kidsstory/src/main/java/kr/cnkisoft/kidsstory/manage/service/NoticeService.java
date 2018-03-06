package kr.cnkisoft.kidsstory.manage.service;

import java.util.List;

import kr.cnkisoft.kidsstory.manage.domain.PreschoolNoticeBoardDto;

public interface NoticeService {
	public List<PreschoolNoticeBoardDto> getNoticeListOfCurrentLoginUser();
	public List<PreschoolNoticeBoardDto> getDiaryListOfCurrentLoginUser();
	public PreschoolNoticeBoardDto getNoticeItemByNoticeId(Integer noticeId);
	public void createNoticeItem(PreschoolNoticeBoardDto preschoolNoticeBoard);
	public void modifyNoticeItem(PreschoolNoticeBoardDto preschoolNoticeBoard);
	public void removeNoticeItem(Integer noticeId);
}
