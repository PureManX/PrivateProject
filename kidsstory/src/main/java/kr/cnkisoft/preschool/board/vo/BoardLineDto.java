package kr.cnkisoft.preschool.board.vo;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardLineDto extends CommonDto{
	private Integer lineId;
	private String schCd;
	private String busId;
	private String lineNm;
	private String lineType;
	private String sttusCd;
	private String teacherId;
	private String busNum;
	private String userNm;
	private String imgSrc;
}
