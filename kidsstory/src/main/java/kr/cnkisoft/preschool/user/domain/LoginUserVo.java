package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.framework.enums.LoginUserType;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserVo{
	private LoginUserType userType = LoginUserType.GUEST;

	private UserVo user;

	public PreschoolVo getSchool() {
		return user.getPreschool();
	}

	public PreschoolClassDto getUserClass() {
		List<PreschoolClassDto> classList = getSchool().getClasses();

		for (PreschoolClassDto preschoolClass : classList) {
			if (user.getClsId().equals(preschoolClass.getClsId())) {
				return preschoolClass;
			}
		}

		return null;
	}

	public List<StudentVo> getChildren() {
		switch (userType) {
			case PARENT:
				return ((ParentVo)user).getChildren();
			case TEACHER:
				return ((TeacherVo)user).getStudentList();
			default:
				return null;
		}

	}

	public Integer getLoginUserId() {
		return user.getUserId();
	}
}
