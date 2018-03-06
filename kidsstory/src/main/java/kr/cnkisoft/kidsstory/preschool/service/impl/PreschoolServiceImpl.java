package kr.cnkisoft.kidsstory.preschool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.cnkisoft.framework.security.AuthUtils;
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

}
