package kr.cnkisoft.preschool.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.framework.enums.MenuType;
import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.common.file.service.FileService;
import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;
import kr.cnkisoft.preschool.manage.domain.PreschoolMenuDto;
import kr.cnkisoft.preschool.manage.mapper.MenuMapper;
import kr.cnkisoft.preschool.manage.service.MenuService;
import kr.cnkisoft.preschool.user.domain.UserVo;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuMapper menuMapper;
	
	@Autowired
	FileService fileService;
	
	@Override
	public List<DailyMenuVo> getWekklyMenuListOfCurrentLoginUser() {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd();
		
		return getWeeklyMenuListByPreschoolCode(preschoolCode);
	}
	
	@Override
	public List<DailyMenuVo> getDailyMenuListOfCurrentLoginUser(String menuDate) {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd();
		
		return getDauilyMenuListByPreschoolCode(preschoolCode, menuDate);
	}

	@Override
	public List<DailyMenuVo> getDailyMenuListOfToday(String menuDate) {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd();
		
		return getDauilyMenuListByPreschoolCode(preschoolCode, menuDate);
	}
	
	List<DailyMenuVo> getWeeklyMenuListByPreschoolCode(String preschoolCode) {
		String startDate = DateUtils.getFirstDayOfWeekString();
		String endDate = DateUtils.getLasttDayOfWeekString();
		
		return menuMapper.selectListWeeklyMenu(preschoolCode, startDate, endDate);
	}

	List<DailyMenuVo> getDauilyMenuListByPreschoolCode(String preschoolCode, String menuDate) {
		return menuMapper.selectListDailyMenu(preschoolCode, menuDate);
	}

	@Override
	public int createMenuItem(String menuType, String menuContent, MultipartFile menuIamge) {
		FileInfoDto menuUploadFile = fileService.createMenuImage(menuIamge);
		
		UserVo loginUser = AuthUtils.getLoginUser().getUser();
		String preschoolCode = loginUser.getPreschool().getSchCd();
		Integer loginUserId = loginUser.getUserId();
		
		String convertedCrToComma = menuContent.replaceAll("\n", ",");
		
		PreschoolMenuDto preschoolMenu = new PreschoolMenuDto();
		
		preschoolMenu.setMenuType(MenuType.fromCode(menuType));
		preschoolMenu.setCreatedBy(loginUserId);
		preschoolMenu.setSchCd(preschoolCode);
		preschoolMenu.setMenuDate(DateUtils.currentDateOfYear());
		preschoolMenu.setMenuContent(convertedCrToComma);
		preschoolMenu.setMenuImgId(menuUploadFile.getFileId());
		return menuMapper.insertMenuItme(preschoolMenu);
	}

	@Override
	public int removeMenuItem(Integer menuId) {
		return menuMapper.deleteMenuItem(menuId);
	}


}
