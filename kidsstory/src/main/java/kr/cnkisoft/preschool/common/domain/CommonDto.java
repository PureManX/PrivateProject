package kr.cnkisoft.preschool.common.domain;

import lombok.Data;

import java.util.Date;


@Data
public class CommonDto {
	private String createdBy;
	private Date createdDt;
	private String updatedBy;
	private Date updatedDt;
}
