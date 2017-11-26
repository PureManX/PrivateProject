package kr.cnkisoft.preschool.board.vo;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(includeSuper = true)
@NoArgsConstructor
public class BoardLineDetailHistDto extends CommonDto{
	private Integer lineDtlHistId;
	private Integer lineDtlId;
	private String histDate;
}
