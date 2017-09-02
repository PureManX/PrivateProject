package kr.cnkisoft.preschool.board.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardLineHistDto {
	private int lineHistId;
	private int lineDtlId;
	private String histDate;
	private String boardDiv;
	private String unbReason;

	private Integer lineId;
}
