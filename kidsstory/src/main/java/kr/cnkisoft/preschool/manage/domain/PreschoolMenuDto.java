package kr.cnkisoft.preschool.manage.domain;

import java.util.Arrays;
import java.util.List;

import kr.cnkisoft.framework.enums.MenuType;
import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolMenuDto extends CommonDto{
	Integer menuId;
	String schCd;
	String menuDate;
	MenuType menuType;
	Integer menuImgId;
	String menuContent;
	String imgSrc;
	
	public PreschoolMenuDto(Integer menuId) {
		this.menuId = menuId;
	}
	
	public List<String> getMenuContentList() {
		return Arrays.asList(menuContent.split(","));
	}
}
