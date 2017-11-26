package kr.cnkisoft.preschool.board.vo;

import java.util.List;

import lombok.Data;

@Data
public class BoardProcessParamVo {
	private Integer lineId;
	private Integer lineDetailId;
	private String histDate;
	
	private List<BoardLineStudentHistDto> processList;
}
