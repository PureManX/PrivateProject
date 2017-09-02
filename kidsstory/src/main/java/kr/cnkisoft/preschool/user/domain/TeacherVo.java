package kr.cnkisoft.preschool.user.domain;

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
public class TeacherVo extends UserVo {

	public TeacherVo(UserDto userDto) {
		BeanUtils.copyProperties(userDto, this);
	}

	private List<StudentVo> studentList;
}
