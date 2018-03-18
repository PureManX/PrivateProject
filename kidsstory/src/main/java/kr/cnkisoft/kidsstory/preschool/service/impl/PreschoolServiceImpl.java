package kr.cnkisoft.kidsstory.preschool.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolBusDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.mapper.PreschoolMapper;
import kr.cnkisoft.kidsstory.preschool.service.PreschoolService;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;

@Service
public class PreschoolServiceImpl implements PreschoolService {

	@Autowired
	PreschoolMapper preschoolMapper;

	@Override
	public List<PreschoolClassDto> getClassListByCurrentLoginPreshcoolCode() {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		return getClassListByPreshcoolCode(loginUser.getSchool().getSchCd());
	}

	@Override
	public List<PreschoolClassDto> getClassListByPreshcoolCode(String schCd) {
		if (StringUtils.isEmpty(schCd)) {
			throw new RuntimeException("유치원 코드가 올바르지 않습니다.");
		}

		return preschoolMapper.selectListPreschoolClassbyPreshcoolCode(schCd);
	}

	@Override
	public void createPreschoolClass(PreschoolClassDto preschoolClass) {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		preschoolClass.setCreatedBy(loginUser.getLoginUserId());
		preschoolClass.setSchCd(loginUser.getSchool().getSchCd());
		preschoolClass.setSchSttusCd("A");

		preschoolMapper.insertPreschoolClass(preschoolClass);

	}

	@Override
	public void modifyPreschoolClass(PreschoolClassDto preschoolClass) {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		preschoolClass.setUpdatedBy(loginUser.getLoginUserId());

		preschoolMapper.updatePreschoolClass(preschoolClass);
	}

	@Override
	public List<PreschoolBusDto> getBusListByCurrentLoginPreshcoolCode() {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		return getBusListByPreshoolCode(loginUser.getSchool().getSchCd());
	}

	@Override
	public List<PreschoolBusDto> getBusListByPreshoolCode(String schCd) {
		List<PreschoolBusDto> busList = preschoolMapper.selectListPreschoolBus();

		return busList.stream().filter(x -> x.getSchCd().equals(schCd)).collect(Collectors.toList());
	}

}
