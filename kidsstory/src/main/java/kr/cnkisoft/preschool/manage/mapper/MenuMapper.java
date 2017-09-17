package kr.cnkisoft.preschool.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;

@Mapper
public interface MenuMapper {
	public List<DailyMenuVo> selectListDailyMenu(@Param("schCd")String schCd);
}
