package kr.cnkisoft.kidsstory.preschool.domain;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
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
}
