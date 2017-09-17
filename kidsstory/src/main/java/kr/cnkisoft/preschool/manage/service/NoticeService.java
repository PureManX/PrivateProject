package kr.cnkisoft.preschool.manage.service;

import java.util.List;

import kr.cnkisoft.preschool.manage.domain.PreschoolNoticeBoardDto;

public interface NoticeService {
	public List<PreschoolNoticeBoardDto> getNoticeListOfCurrentLoginUser();
}
