package kr.cnkisoft.preschool.user.service;

import java.util.List;

import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.user.domain.*;

public interface UserService {
	public LoginUserVo getLoginUser(String contact);
	public List<StudentDto> getStudentListByTeacherContact(String contact);
	public CommonResultVo createReqMedi(ReqMediDto param);
	public List<ReqMediVo> getListReqMediByTeacherId(int teacherId);
	public int createParent(UserDto parent);
	public List<UserDto> getListParentInPreschool();
	public UserDto getUser(Integer userId);
}
