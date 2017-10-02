package kr.cnkisoft.preschool.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;

@Mapper
public interface MenuMapper {
	public List<DailyMenuVo> selectListDailyMenu(@Param("schCd")String schCd, @Param("menuDate")String menuDate);
	public List<DailyMenuVo> selectListWeeklyMenu(@Param("schCd")String schCd
			, @Param("startDate")String startDate, @Param("endDate")String endDate);
}
