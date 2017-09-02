package kr.cnkisoft.preschool.board.vo;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardLineServiceDto extends CommonDto{
	private Integer lineServiceId;
	private Integer lineId;
	private Integer serviceTeacherId;
	private Date serviceStartDt;
	private Date serviceEndDt;
}
