package kr.cnkisoft.kidsstory.manage.domain;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolScheduleDto extends CommonDto{
	Integer scheduleId;
	String schCd;
	String scheduleType;
	String scheduleDate;
	String scheduleContent;
}
