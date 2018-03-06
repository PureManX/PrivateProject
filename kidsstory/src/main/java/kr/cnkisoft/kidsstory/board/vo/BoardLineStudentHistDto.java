package kr.cnkisoft.kidsstory.board.vo;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(includeSuper = true)
@NoArgsConstructor
public class BoardLineStudentHistDto extends CommonDto{
	private Integer lineDtlStduHistId;
	private Integer lineDtlId;
	private Integer userId;
	private String histDate;
	private String boardDiv;
	private String unbReason;

	private Integer lineId;
}
