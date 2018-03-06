package kr.cnkisoft.kidsstory.board.vo;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BoardLineDto extends CommonDto{
	protected Integer lineId;
	protected String schCd;
	protected Integer busId;
	protected String lineNm;
	protected String lineType;
	protected String sttusCd;
	
	public BoardLineDto(Integer lineId) {
		super();
		this.lineId = lineId;
	}
//	private String teacherId;
//	private String busNum;
//	private String userNm;
//	private String imgSrc;
}
