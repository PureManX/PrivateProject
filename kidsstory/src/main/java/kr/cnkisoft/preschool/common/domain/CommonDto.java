package kr.cnkisoft.preschool.common.domain;

import lombok.Data;

import java.util.Date;


@Data
public class CommonDto {
	private Integer createdBy;
	private Date createdDt;
	private Integer updatedBy;
	private Date updatedDt;
}
