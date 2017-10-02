package kr.cnkisoft.preschool.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.manage.domain.PreschoolNoticeBoardDto;
import kr.cnkisoft.preschool.manage.mapper.NoticeMapper;
import kr.cnkisoft.preschool.manage.service.NoticeService;

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

}
