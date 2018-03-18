package kr.cnkisoft.kidsstory.preschool.service;

import java.util.List;

import kr.cnkisoft.kidsstory.preschool.domain.PreschoolBusDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;

public interface PreschoolService {

	// 학급
	public List<PreschoolClassDto> getClassListByCurrentLoginPreshcoolCode();
	public List<PreschoolClassDto> getClassListByPreshcoolCode(String schCd);
	public void createPreschoolClass(PreschoolClassDto preschoolClass);
	public void modifyPreschoolClass(PreschoolClassDto preschoolClass);

	// 버스
	public List<PreschoolBusDto> getBusListByCurrentLoginPreshcoolCode();
	public List<PreschoolBusDto> getBusListByPreshoolCode(String schCd);
}
