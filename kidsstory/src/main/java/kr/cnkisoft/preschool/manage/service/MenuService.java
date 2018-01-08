package kr.cnkisoft.preschool.manage.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;

public interface MenuService {
	public List<DailyMenuVo> getWekklyMenuListOfCurrentLoginUser();
	public List<DailyMenuVo> getDailyMenuListOfCurrentLoginUser(String menuDate);
	public List<DailyMenuVo> getDailyMenuListOfToday(String menuDate);
	public int createMenuItem(String menuType, String menuContent, MultipartFile menuIamge);
	public int removeMenuItem(Integer menuId);
}
