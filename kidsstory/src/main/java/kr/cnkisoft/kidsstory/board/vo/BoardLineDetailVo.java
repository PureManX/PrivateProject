package kr.cnkisoft.kidsstory.board.vo;

import java.util.List;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class BoardLineDetailVo {
	private Integer lineDtlId;
	private String boardTm;
	private String boardLoc;
	private Integer boardOrder;
	private String boardHistDate;
	
	private List<BoardStudentVo> studentList;

	protected BoardLineDetailVo(Integer lineDtlId) {
		super();
		this.lineDtlId = lineDtlId;
	}
	
	public boolean isBoardComplete() {
		return StringUtils.isEmpty(boardHistDate) ? false : true;
	}
}
