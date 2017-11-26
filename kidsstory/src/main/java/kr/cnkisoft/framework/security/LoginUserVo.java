package kr.cnkisoft.framework.security;

import java.util.List;

import kr.cnkisoft.preschool.user.domain.PreschoolClassDto;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.domain.UserVo;

public interface LoginUserVo {
	
	public UserVo getUser();
	public Integer getLoginUserId();
	public PreschoolClassDto getUserClass();
	public List<StudentVo> getChildren();
}
