package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PreSchoolDto extends CommonDto {
	private Integer clsId;
	private String clsNm;
	private String schCd;
	private Integer hrTeacherId;
	private String sttusCd;
	private String schName;
	private String schSttusCd;
}
