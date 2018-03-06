package kr.cnkisoft.kidsstory.board.vo;

import lombok.Data;

@Data
public class BoardLineWithDetailVo {
	private Integer lineId;
	private String schCd;
	private Integer busId;
	private String lineNm;
	private String lineType;
	private String sttusCd;
	private String teacherId;
	private String busNum;
	private String userNm;
	private String imgSrc;
	private Integer lineDtlId;
	private String boardTm;
	private String boardLoc;
	private Integer stduId;
	private Integer boardOrder;
}
