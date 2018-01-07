package kr.cnkisoft.preschool.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.common.file.domain.DailyGalleryListVo;
import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.common.file.service.FileService;
import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.push.mapper.PushMapper;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.PreschoolClassDto;
import kr.cnkisoft.preschool.user.domain.PreschoolVo;
import kr.cnkisoft.preschool.user.domain.ReqMediDto;
import kr.cnkisoft.preschool.user.domain.ReqMediVo;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.domain.TeacherVo;
import kr.cnkisoft.preschool.user.domain.UserDto;
import kr.cnkisoft.preschool.user.domain.UserVo;
import kr.cnkisoft.preschool.user.mapper.UserMapper;
import kr.cnkisoft.preschool.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PushMapper pushMapper;
	
	@Autowired
	FileService fileService;
	
	@Override
	public LoginUserVo getLoginUser(String contact) {
		LoginUserVo loginUserVo = new LoginUserVo(getUserByContact(contact));

		return loginUserVo;
	}

	private UserVo getUserByContact(String contact) {
		UserVo user = userMapper.selectUserbyContact(contact);

		if (user == null) {
			return createGuestUser(contact);
		}
		
		if ("PAR".equals(user.getUserType())) {
			user = getParent(user);
		} else if ("TCH".equals(user.getUserType())) {
			user = getTeacher(user);
		}

		user.setPreschool(getPreschool(user));

		return user;
	}

	private PreschoolVo getPreschool(UserVo user) {
		PreschoolVo preschool = null;

		if (user != null && user.getClsId() != null) {
			preschool = userMapper.selectPreschoolbyClsId(user.getClsId());
			List<PreschoolClassDto> preschoolClassList = userMapper.selectListPreschoolClassbyPreshcoolCode(preschool.getSchCd());

			preschool.setClasses(preschoolClassList);

		}

		return  preschool;
	}

	private ParentVo getParent(UserVo user) {
		ParentVo parentVo = new ParentVo(user);
		parentVo.setPushInfo(getPushInfo(user.getUserId()));
		parentVo.setChildren(getChildrenByParentId(user.getUserId()));

		return parentVo;
	}

	private StudentVo getStudentVo(UserVo user) {
		StudentVo studentVo = new StudentVo(user);

		studentVo.setParents(userMapper.selectListParentsByChildId(user.getUserId()));

		return studentVo;
	}

	private TeacherVo getTeacher(UserVo user) {
		TeacherVo teacherVo = new TeacherVo(user);
		teacherVo.setStudentList(getStudentListByTeacherContact(user.getContact()));

		return teacherVo;
	}
	
	private UserVo createGuestUser(String contact) {
		UserVo userVo = new UserVo();
		
		userVo.setContact(contact);
		userVo.setUserType(LoginUserType.GUEST.getCode());
		
		return userVo;
	}

	private List<StudentVo> getChildrenByParentId(Integer parentId) {
		List<StudentVo> studentList = userMapper.selectListStudentByParentId(parentId);

//		List<StudentVo> studentList = new ArrayList<>();
//
//		for (UserDto userDto : studentDtoList) {
//			StudentVo studentVo = new StudentVo(userDto);
//			studentList.add(studentVo);
//		}

		return studentList;
	}

	@Override
	public List<StudentVo> getStudentListByTeacherContact(String contact) {
		List<StudentVo> studentList = userMapper.selectListStudentByTeacherContact(contact);

		return studentList;
	}
	
	@Override
	public List<StudentVo> getStudentListByBoardLineDetailId(Integer lineDetailId) {
		List<StudentVo> studentList = userMapper.selectListStudentByBoardLineDetailId(lineDetailId);
		return studentList;
	}
	
	@Override
	public CommonResultVo createReqMedi(ReqMediDto param) {
		userMapper.insertReqMediInfo(param);

		return new CommonResultVo();
	}

	@Override
	public List<ReqMediVo> getListReqMediByTeacherId(Integer teacherId) {
		return userMapper.selectListReqMediByTeacherId(teacherId);
	}

	@Override
	public int createParent(UserDto parent) {
		parent.setUserType("PAR");
		parent.setSttusCd("A");
		parent.setSchCd(AuthUtils.getLoginUser().getSchool().getSchCd());
		parent.setCreatedBy(AuthUtils.getLoginUser().getUser().getUserId());

		return userMapper.insertUser(parent);
	}

	@Override
	public List<UserDto> getListParentInPreschool() {
		String preschoolCode = AuthUtils.getLoginUser().getSchool().getSchCd();

		return userMapper.selectListParentInPreschool(preschoolCode);
	}

	@Override
	public UserVo getUser(Integer userId) {
		return getTargetUser(userId);
	}

	@Override
	public PreSchoolPushIdDto getPushInfoByLineDetailId(Integer lineDetailId) {
		PreSchoolPushIdDto pushInfo = null;

		List<PreSchoolPushIdDto> pushInfoList = pushMapper.selectListPushInfoByLineDetailId(lineDetailId);

		if (!CollectionUtils.isEmpty(pushInfoList)) {
			pushInfo = pushInfoList.get(0);
		}

		return pushInfo;
	}

	private UserVo getTargetUser(Integer userId) {
		UserVo targetuser = userMapper.selectUserbyUserId(userId);
		UserVo returnUser = null;
		if (targetuser != null) {

			if ("STU".equals(targetuser.getUserType())) {
				returnUser = getStudentVo(targetuser);
			} else if ("TCH".equals(targetuser.getUserType())) {
				returnUser = getTeacher(targetuser);
			}

//			List<PreSchoolPushIdDto> pushInfoList = pushMapper.selectListPushInfoByUserId(userId);
//
//			if (!CollectionUtils.isEmpty(pushInfoList)) {
//				PreSchoolPushIdDto pushInfo = pushInfoList.get(0);
//				targetuser.setPushInfo(pushInfo);
//			}
		}

		return returnUser;
	}

	private PreSchoolPushIdDto getPushInfo(Integer userId) {
		PreSchoolPushIdDto pushInfo = null;

		List<PreSchoolPushIdDto> pushInfoList = pushMapper.selectListPushInfoByUserId(userId);

		if (!CollectionUtils.isEmpty(pushInfoList)) {
			pushInfo = pushInfoList.get(0);
		}

		return pushInfo;
	}

	@Override
	public List<FileInfoDto> getImageListOfClass(Integer classId) {
		return fileService.getFileListByClass(classId);
	}
	
	@Override
	public List<DailyGalleryListVo> getDailyImageListOfClass(Integer classId) {
		return fileService.getDailyGalleryListByClass(classId);
	}

	@Override
	public FileInfoDto updateStudentProfileImage(MultipartFile file, Integer studentId) {
		UserDto student = userMapper.selectUserbyUserId(studentId);
		
		FileInfoDto uploadFileInfo = fileService.createProfileImage(file);
		
		student.setProfImgId(uploadFileInfo.getFileId());
		
		userMapper.updateUserProfileImageId(student);
		
		return uploadFileInfo;
	}

}
