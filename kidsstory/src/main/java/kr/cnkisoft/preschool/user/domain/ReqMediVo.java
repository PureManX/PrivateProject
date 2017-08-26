package kr.cnkisoft.preschool.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReqMediVo extends ReqMediDto {
	private String userNm;
	private String imgSrc;
}
