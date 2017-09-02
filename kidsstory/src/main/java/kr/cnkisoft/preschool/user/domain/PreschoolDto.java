package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolDto extends CommonDto {
	private String schCd;
	private String sttusCd;
	private String schName;
//	private Integer clsId;
//	private String clsNm;
// 	private Integer hrTeacherId;
// 	private String schSttusCd;
}
