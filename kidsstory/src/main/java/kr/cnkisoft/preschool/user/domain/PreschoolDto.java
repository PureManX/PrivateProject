package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PreschoolDto extends CommonDto {
	protected String schCd;
	protected String sttusCd;
	protected String schName;
//	private Integer clsId;
//	private String clsNm;
// 	private Integer hrTeacherId;
// 	private String schSttusCd;
}
