package kr.cnkisoft.preschool.manage.domain;

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
	Integer menuType;
	Integer menuImgId;
	String menuContent;
	String imgSrc;
	
	public PreschoolMenuDto(Integer menuId) {
		this.menuId = menuId;
	}
}
