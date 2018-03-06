package kr.cnkisoft.kidsstory.preschool.service;

import java.util.List;

import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;

public interface PreschoolService {
	public List<PreschoolClassDto> getClassListByCurrentLoginPreshcoolCode();
	public List<PreschoolClassDto> getClassListByPreshcoolCode(String schCd);
}
