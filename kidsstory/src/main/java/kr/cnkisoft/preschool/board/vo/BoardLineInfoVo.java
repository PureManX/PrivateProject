package kr.cnkisoft.preschool.board.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by PureMaN on 2017-08-31.
 */
@Data
@Builder
public class BoardLineInfoVo {
	BoardLineDto line;
	PreschoolBusDto bus;
}
