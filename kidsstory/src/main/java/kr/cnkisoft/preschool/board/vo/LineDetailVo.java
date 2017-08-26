package kr.cnkisoft.preschool.board.vo;

import lombok.Data;

@Data
public class LineDetailVo {
	private int lineDtlId;
	private int lineId;
	private int profImgId;
	private String imgSrc;
	private String userNm;
	private String boardTm;
	private String boardLoc;
	private int boardOrder;
	private String boardDiv;
	private String clsNm;
	private String fileNm;
	private String fileType;
	

	public String getImgSrc() {
		return "/" + fileType.toLowerCase() + "/" + fileNm;
	}
}
