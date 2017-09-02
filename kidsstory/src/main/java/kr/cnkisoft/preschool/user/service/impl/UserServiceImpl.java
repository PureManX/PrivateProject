package kr.cnkisoft.preschool.user.service.impl;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.push.mapper.PushMapper;
import kr.cnkisoft.preschool.user.domain.*;
import kr.cnkisoft.preschool.user.mapper.UserMapper;
import kr.cnkisoft.preschool.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PushMapper pushMapper;
	
	@Override
	public LoginUserVo getLoginUser(String contact) {
		LoginUserVo loginUserVo = new LoginUserVo();
		UserDto user = userMapper.selectUserbyContact(contact);

		if (user != null) {
			loginUserVo.setUser(getUserByContact(contact));

//			PreschoolDto preschoolDto = userMapper.selectPreschoolbyClsId(user.getClsId());


			if ("PAR".equals(user.getUserType())) {
//				List<StudentVo> children = userMapper.selectListStudentByParentId(user.getUserId());
//				loginUserVo.setChildren(children);

				loginUserVo.setUserType(LoginUserType.PARENT);
			} else if ("TCH".equals(user.getUserType())) {
//				List<StudentVo> children = userMapper.selectListStudentByTeacherContact(user.getContact());
//				loginUserVo.setChildren(children);

				loginUserVo.setUserType(LoginUserType.TEACHER);
			}

		}

		return loginUserVo;
	}

	private UserVo getUserByContact(String contact) {
		UserVo user = null;
		UserDto userDto = userMapper.selectUserbyContact(contact);

		if ("PAR".equals(userDto.getUserType())) {
			user = getParent(userDto);
		} else if ("TCH".equals(userDto.getUserType())) {
			user = getTeacher(userDto);
		}

		user.setPreschool(getPreschool(userDto));

		return user;
	}

	private PreschoolVo getPreschool(UserDto userDto) {
		PreschoolVo preschool = null;

		if (userDto != null && userDto.getClsId() != null) {
			preschool = userMapper.selectPreschoolbyClsId(userDto.getClsId());
			List<PreschoolClassDto> preschoolClassList = userMapper.selectListPreschoolClassbyPreshcoolCode(preschool.getSchCd());

			preschool.setClasses(preschoolClassList);

		}

		return  preschool;
	}

	private ParentVo getParent(UserDto userDto) {
		ParentVo parentVo = new ParentVo(userDto);
		parentVo.setPushInfo(getPushInfo(userDto.getUserId()));
		parentVo.setChildren(getChildrenByParentId(userDto.getUserId()));

		return parentVo;
	}

	private StudentVo getStudentVo(UserDto userDto) {
		StudentVo studentVo = new StudentVo(userDto);

		studentVo.setParents(userMapper.selectListParentsByChildId(userDto.getUserId()));

		return studentVo;
	}

	private TeacherVo getTeacher(UserDto userDto) {
		TeacherVo teacherVo = new TeacherVo(userDto);
		teacherVo.setStudentList(getStudentListByTeacherContact(userDto.getContact()));

		return teacherVo;
	}

	private List<StudentVo> getChildrenByParentId(Integer parentId) {
		List<UserDto> studentDtoList = userMapper.selectListStudentByParentId(parentId);

		List<StudentVo> studentList = new ArrayList<>();

		for (UserDto userDto : studentDtoList) {
			StudentVo studentVo = new StudentVo(userDto);
			studentList.add(studentVo);
		}

		return studentList;
	}

	@Override
	public List<StudentVo> getStudentListByTeacherContact(String contact) {
		List<UserDto> studentDtoList = userMapper.selectListStudentByTeacherContact(contact);

		List<StudentVo> studentList = new ArrayList<>();

		for (UserDto userDto : studentDtoList) {
			StudentVo studentVo = new StudentVo(userDto);
			studentList.add(studentVo);

			ArrayList<ParentVo> parents = new ArrayList<>();

			parents.add(getParent(userDto));

			studentVo.setParents(parents);
		}

		return studentList;
	}

	@Override
	public CommonResultVo createReqMedi(ReqMediDto param) {
		userMapper.insertReqMediInfo(param);

		return new CommonResultVo();
	}

	@Override
	public List<ReqMediVo> getListReqMediByTeacherId(int teacherId) {
		return userMapper.selectListReqMediByTeacherId(teacherId);
	}

	@Override
	public int createParent(UserDto parent) {
		parent.setUserType("PAR");
		parent.setSttusCd("A");
		parent.setSchCd(AuthUtils.getLoginUser().getSchool().getSchCd());
		parent.setCreatedBy(String.valueOf(AuthUtils.getLoginUser().getUser().getUserId()));

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
		UserDto targetuser = userMapper.selectUserbyUserId(userId);
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
}
