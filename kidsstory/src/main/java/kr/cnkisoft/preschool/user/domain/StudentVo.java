package kr.cnkisoft.preschool.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentVo extends UserVo{

	public StudentVo(UserDto userDto) {
		BeanUtils.copyProperties(userDto, this);
	}

	private List<ParentVo> parents;
}
