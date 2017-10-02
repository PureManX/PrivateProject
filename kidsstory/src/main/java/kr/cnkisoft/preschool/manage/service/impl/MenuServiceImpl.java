package kr.cnkisoft.preschool.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;
import kr.cnkisoft.preschool.manage.mapper.MenuMapper;
import kr.cnkisoft.preschool.manage.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuMapper menuMapper;
	
	@Override
	public List<DailyMenuVo> getMenuListOfCurrentLoginUser() {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd(); 
		return getMenuListByPreschoolCode(preschoolCode);
	}

	List<DailyMenuVo> getMenuListByPreschoolCode(String preschoolCode) {
		return menuMapper.selectListDailyMenu(preschoolCode);
	}
}