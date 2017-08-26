package kr.cnkisoft.preschool.user.service.impl;

import java.util.List;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.user.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.preschool.common.domain.CommonResultVo;
import kr.cnkisoft.preschool.user.mapper.UserMapper;
import kr.cnkisoft.preschool.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public LoginUserVo getLoginUser(String contact) {
		LoginUserVo loginUserVo = new LoginUserVo();
		UserDto user = userMapper.selectUserbyContact(contact);

		if (user != null) {
			loginUserVo.setUser(user);

			PreSchoolDto preSchoolDto = userMapper.selectPreschoolbyClsId(user.getClsId());

			if ("PAR".equals(user.getUserType())) {
				List<StudentDto> children = userMapper.selectListStudentByParentId(user.getUserId());

				loginUserVo.setChildren(children);

				loginUserVo.setUserType(LoginUserType.PARENT);
			} else if ("TCH".equals(user.getUserType())) {
				List<StudentDto> children = userMapper.selectListStudentByTeacherContact(user.getContact());

				loginUserVo.setChildren(children);

				loginUserVo.setUserType(LoginUserType.TEACHER);
			}

			loginUserVo.setSchool(preSchoolDto);
		}

		return loginUserVo;
	}

	@Override
	public List<StudentDto> getStudentListByTeacherContact(String contact) {
		return userMapper.selectListStudentByTeacherContact(contact);
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
	public UserDto getUser(Integer userId) {
		return getTarUserDto(userId);
	}

	private UserDto getTarUserDto(Integer userId) {
		UserDto targetuser = userMapper.selectUserbyUserId(userId);

		if (targetuser != null) {

			if ("STU".equals(targetuser.getUserType())) {
				targetuser = userMapper.selectStudentbyUserId(userId);
			}
		}

		return targetuser;
	}
}
