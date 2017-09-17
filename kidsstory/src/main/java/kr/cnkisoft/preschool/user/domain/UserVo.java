package kr.cnkisoft.preschool.user.domain;

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
