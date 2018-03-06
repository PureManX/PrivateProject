package kr.cnkisoft.kidsstory.manage.domain;

import java.util.Arrays;
import java.util.List;

import groovy.transform.builder.Builder;
import kr.cnkisoft.framework.enums.MenuType;
import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
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
