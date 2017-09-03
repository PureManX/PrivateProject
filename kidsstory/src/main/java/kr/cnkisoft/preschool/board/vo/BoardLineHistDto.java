package kr.cnkisoft.preschool.board.vo;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(includeSuper = true)
@NoArgsConstructor
public class BoardLineHistDto extends CommonDto{
	private Integer lineHistId;
	private Integer lineDtlId;
	private String histDate;
	private String boardDiv;
	private String unbReason;

	private Integer lineId;
}
