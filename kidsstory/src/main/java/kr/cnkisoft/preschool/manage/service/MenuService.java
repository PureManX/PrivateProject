package kr.cnkisoft.preschool.manage.service;

import java.util.List;

import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;

public interface MenuService {
	public List<DailyMenuVo> getMenuListOfCurrentLoginUser();
}
