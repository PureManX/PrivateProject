package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParentVo extends UserVo {

	public ParentVo() {

	}

	public ParentVo(UserDto userDto) {
		BeanUtils.copyProperties(userDto, this);
	}

	private PreSchoolPushIdDto pushInfo;
	private List<StudentVo> children;
}
