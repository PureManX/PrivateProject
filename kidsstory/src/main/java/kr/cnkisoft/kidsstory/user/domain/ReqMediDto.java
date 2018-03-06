package kr.cnkisoft.kidsstory.user.domain;

import java.sql.Date;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqMediDto extends CommonDto {
	private int mediId;
	private int userId;
	private String symptom;
	private Date adminDt;
	private String adminTm;
	private String adminCapa;
	private String adminType;
	private int adminCnt;
	private String sympDesc;
	private String keepType;
}
