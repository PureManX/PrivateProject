package kr.cnkisoft.preschool.admin.domain;

import java.io.Serializable;
import java.util.List;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.PreschoolClassDto;
import kr.cnkisoft.preschool.user.domain.PreschoolVo;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.domain.TeacherVo;
import kr.cnkisoft.preschool.user.domain.UserVo;
import lombok.Data;
import lombok.NoArgsConstructor;

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
