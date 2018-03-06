package kr.cnkisoft.framework.security;

import java.util.List;

import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.user.domain.StudentVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;

public interface LoginUserVo {
	
	public UserVo getUser();
	public Integer getLoginUserId();
	public PreschoolClassDto getUserClass();
	public List<StudentVo> getChildren();
}
