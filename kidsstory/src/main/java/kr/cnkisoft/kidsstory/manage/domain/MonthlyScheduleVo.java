package kr.cnkisoft.kidsstory.manage.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyScheduleVo {
	String month;
	List<PreschoolScheduleDto> scheduleList;

	public MonthlyScheduleVo(String month) {
		this.month = month;
	}
	
	public String getFormattedMonth() {
		return month.substring(0, 4) + "년 " + month.substring(4) + "월";
	}
}
