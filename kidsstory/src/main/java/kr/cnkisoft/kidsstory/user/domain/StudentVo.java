package kr.cnkisoft.kidsstory.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo extends UserVo {

	private List<ParentVo> parents;

	public StudentVo(UserDto userDto) {
		BeanUtils.copyProperties(userDto, this);
	}

	public StudentVo(Integer userId) {
		super.userId = userId;
	}
}
