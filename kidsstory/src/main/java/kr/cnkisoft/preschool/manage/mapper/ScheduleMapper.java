package kr.cnkisoft.preschool.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.cnkisoft.preschool.manage.domain.PreschoolScheduleDto;

@Mapper
public interface ScheduleMapper {
	public List<PreschoolScheduleDto> selectListScheduleByPreschoolCode(@Param("schCd")String schCd);
	public int insertSchedule(PreschoolScheduleDto preschoolSchedule);
	public int deleteScheduleByScheduleId(@Param("scheduleId")Integer scheduleId);
}
