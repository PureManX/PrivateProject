package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.framework.enums.LoginUserType;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserVo{
	private LoginUserType userType = LoginUserType.GUEST;
	private UserDto user;
	private UserDto director;
	private UserDto hrTeacher;
	private PreSchoolDto school;
	private List<StudentDto> children;
}
