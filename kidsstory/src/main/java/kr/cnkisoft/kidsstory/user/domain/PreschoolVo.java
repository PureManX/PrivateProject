package kr.cnkisoft.kidsstory.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PreschoolVo extends PreschoolDto {
	protected List<PreschoolClassDto> classes;	// 학급
	protected TeacherVo director;				// 원장 선생님

	public List<TeacherVo> getTeachers() {
		return null;
	}

	public PreschoolClassDto getClassByClassId() {
		return null;
	}
}
