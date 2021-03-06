package kr.cnkisoft.kidsstory.board.vo;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardLineDetailDto extends CommonDto{
	private Integer lineDtlId;
	private Integer lineId;
	private String boardTm;
	private String boardLoc;
	private Integer stduId;
	private Integer boardOrder;
}
