package kr.cnkisoft.kidsstory.board.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;

@Data
@EqualsAndHashCode(callSuper = false)
public class BoardLineServiceDto extends CommonDto{
	private Integer lineServiceId;
	private Integer lineId;
	private Integer serviceTeacherId;
	private Date serviceStartDt;
	private Date serviceEndDt;
}
