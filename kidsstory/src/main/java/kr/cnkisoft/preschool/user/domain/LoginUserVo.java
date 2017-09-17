package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.framework.enums.LoginUserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginUserVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private LoginUserType userType = LoginUserType.GUEST;

	private UserVo user;
	
	public LoginUserVo(UserVo user) {
		this.user = user;
		this.userType = LoginUserType.fromCode(user.getUserType());
	}
	
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
