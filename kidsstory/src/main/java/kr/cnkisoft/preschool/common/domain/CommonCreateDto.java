package kr.cnkisoft.preschool.common.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.cnkisoft.framework.utils.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CommonCreateDto {
	protected Integer createdBy;
	protected Date createdDt;
	
	@JsonIgnore
	public String getCreatedDate() {
		return DateUtils.formattedDateString(createdDt, "yyyy년 MM월 dd일");
	}
}
