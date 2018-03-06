package kr.cnkisoft.kidsstory.user.domain;

import kr.cnkisoft.kidsstory.preschool.domain.PreschoolVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class UserVo extends UserDto {
	protected PreschoolVo preschool;
}