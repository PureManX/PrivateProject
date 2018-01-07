package kr.cnkisoft.preschool.board.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLineIndivualInfoVo {
	private BoardLineWithDetailVo attDetail;
	private BoardLineWithDetailVo comDetail;
}
