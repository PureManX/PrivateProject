package kr.cnkisoft.preschool.manage.service;

import java.util.List;

import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;
import kr.cnkisoft.preschool.manage.domain.PreschoolMenuDto;

public interface MenuService {
	public List<DailyMenuVo> getWekklyMenuListOfCurrentLoginUser();
	public List<DailyMenuVo> getDailyMenuListOfCurrentLoginUser(String menuDate);
	public List<DailyMenuVo> getDailyMenuListOfToday(String menuDate);
	public int createMenuItem(PreschoolMenuDto PreschoolMenu);
	public int removeMenuItem(Integer menuId);
}
