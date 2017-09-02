package kr.cnkisoft.preschool.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolVo extends PreschoolDto {
	private List<PreschoolClassDto> classes;	// 학급
	private TeacherVo director;					// 원장 선생님

	public List<TeacherVo> getTeachers() {
		return null;
	}

	public PreschoolClassDto getClassByClassId() {
		return null;
	}
}
