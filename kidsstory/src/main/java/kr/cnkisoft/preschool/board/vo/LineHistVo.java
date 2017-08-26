package kr.cnkisoft.preschool.board.vo;

import lombok.Data;

@Data
public class LineHistVo {
	private int lineHistId;
	private int lineDtlId;
	private String histDate;
	private String boardDiv;
	private String unbReason;
}
