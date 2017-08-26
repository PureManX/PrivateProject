package kr.cnkisoft.preschool.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentDto extends UserDto{
	private String parNm;
	private String parContact;
}
