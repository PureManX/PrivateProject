package kr.cnkisoft.kidsstory.board.vo;

import groovy.transform.builder.Builder;
import kr.cnkisoft.kidsstory.common.domain.CommonCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class BoardLineStudentRelationDto extends CommonCreateDto{
	private Integer mapId;
	private Integer lineDtlId;
	private Integer stduId;
}
