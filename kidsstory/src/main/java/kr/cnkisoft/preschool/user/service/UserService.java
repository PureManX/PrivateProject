package kr.cnkisoft.preschool.user.service;

import java.util.List;

import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.user.domain.*;

public interface UserService {
	public LoginUserVo getLoginUser(String contact);
	public List<StudentVo> getStudentListByTeacherContact(String contact);
	public List<StudentVo> getStudentListByBoardLineDetailId(Integer lineDetailId); 
	public CommonResultVo createReqMedi(ReqMediDto param);
	public List<ReqMediVo> getListReqMediByTeacherId(Integer teacherId);
	public int createParent(UserDto parent);
	public List<UserDto> getListParentInPreschool();
	public UserVo getUser(Integer userId);
	public PreSchoolPushIdDto getPushInfoByLineDetailId(Integer lineDetailId);
	public List<FileInfoDto> getImageListOfClass(Integer classId);
}
