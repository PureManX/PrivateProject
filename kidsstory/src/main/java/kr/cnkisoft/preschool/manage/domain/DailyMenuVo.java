package kr.cnkisoft.preschool.manage.domain;

import java.util.List;

import kr.cnkisoft.framework.utils.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyMenuVo {
	String schCd;
	String menuDate;
	List<PreschoolMenuDto> menuDetailList;

	public DailyMenuVo(String menuDate) {
		this.menuDate = menuDate;
	}
	
	public String getFormattedMenuDate() {
		return DateUtils.convertStringDateFromString(this.menuDate, "yyyyMMdd", "yyyy년 MM월 dd일 (E)");
	}
}
