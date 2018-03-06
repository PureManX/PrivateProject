package kr.cnkisoft.kidsstory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.common.file.domain.DailyGalleryListVo;
import kr.cnkisoft.kidsstory.common.file.domain.FileInfoDto;
import kr.cnkisoft.kidsstory.common.file.service.FileService;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolVo;
import kr.cnkisoft.kidsstory.preschool.mapper.PreschoolMapper;
import kr.cnkisoft.kidsstory.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.kidsstory.push.mapper.PushMapper;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.ParentVo;
import kr.cnkisoft.kidsstory.user.domain.ReqMediDto;
import kr.cnkisoft.kidsstory.user.domain.ReqMediVo;
import kr.cnkisoft.kidsstory.user.domain.StudentVo;
import kr.cnkisoft.kidsstory.user.domain.TeacherVo;
import kr.cnkisoft.kidsstory.user.domain.UserDto;
import kr.cnkisoft.kidsstory.user.domain.UserVo;
import kr.cnkisoft.kidsstory.user.mapper.UserMapper;
import kr.cnkisoft.kidsstory.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	PreschoolMapper preschoolMapper;

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
			preschool = preschoolMapper.selectPreschoolbyClsId(user.getClsId());
			List<PreschoolClassDto> preschoolClassList = preschoolMapper.selectListPreschoolClassbyPreshcoolCode(preschool.getSchCd());

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
