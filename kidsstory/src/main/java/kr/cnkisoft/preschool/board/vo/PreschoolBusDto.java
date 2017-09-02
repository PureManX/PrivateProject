package kr.cnkisoft.preschool.board.vo;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PreschoolBusDto extends CommonDto{
	private Integer busId;
	private String schCd;
	private String busNum;
	private String driverName;
	private String sttusCd;
}
