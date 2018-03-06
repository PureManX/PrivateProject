package kr.cnkisoft.kidsstory.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.common.file.domain.DailyGalleryListVo;
import kr.cnkisoft.kidsstory.common.file.domain.FileInfoDto;
import kr.cnkisoft.kidsstory.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.kidsstory.user.domain.*;

public interface UserService {
	public LoginUserVo getLoginUser(String contact);
	public List<StudentVo> getStudentListByCurrentLoginPreshcoolCode();
	public List<StudentVo> getStudentListByPreshcoolCode(String schCd);
	public List<StudentVo> getStudentListByTeacherContact(String contact);
	public List<StudentVo> getStudentListByBoardLineDetailId(Integer lineDetailId); 
	public CommonResultVo createReqMedi(ReqMediDto param);
	public List<ReqMediVo> getListReqMediByTeacherId(Integer teacherId);
	public int createParent(UserDto parent);
	public List<UserDto> getListParentInPreschool();
	public UserVo getUser(Integer userId);
	public PreSchoolPushIdDto getPushInfoByLineDetailId(Integer lineDetailId);
	public List<FileInfoDto> getImageListOfClass(Integer classId);
	public List<DailyGalleryListVo> getDailyImageListOfClass(Integer classId);
	public FileInfoDto updateStudentProfileImage(MultipartFile file, Integer studentId);
	
	public void createStudent(StudentVo student);
	public void modifyStudent(StudentVo student);
	public void modifyParnet(UserDto parent);
}
