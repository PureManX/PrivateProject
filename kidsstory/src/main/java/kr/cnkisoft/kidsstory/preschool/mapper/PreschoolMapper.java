package kr.cnkisoft.kidsstory.preschool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.kidsstory.preschool.domain.PreschoolBusDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolVo;

@Mapper
public interface PreschoolMapper {
	// 유치원 학급 정보 조회
	@Select("SELECT * FROM PRESCH_CLASS WHERE SCH_CD = #{schCd}")
	public List<PreschoolClassDto> selectListPreschoolClassbyPreshcoolCode(@Param("schCd")String schCd);

	// 유치원 학급 정보 조회
	@Select("SELECT sch.* FROM PRESCH_CLASS cls JOIN PRESCH_INFO sch ON cls.SCH_CD = sch.SCH_CD WHERE cls.CLS_ID = #{clsId}")
	public PreschoolVo selectPreschoolbyClsId(@Param("clsId")Integer clsId);

	public int insertPreschoolClass(PreschoolClassDto preschoolClass);
	public int updatePreschoolClass(PreschoolClassDto preschoolClass);

	public List<PreschoolBusDto> selectListPreschoolBus();
	public List<PreschoolDto> selectListPreschool();

	public int insertPreschool(PreschoolDto preschool);
	public int updatePreschool(PreschoolDto preschool);

}
