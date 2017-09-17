package kr.cnkisoft.preschool.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.preschool.manage.domain.PreschoolNoticeBoardDto;

@Mapper
public interface NoticeMapper {
	@Select("SELECT * FROM PRESCH_NOTICE_BOARD WHERE SCH_CD = #{schCd}")
	public List<PreschoolNoticeBoardDto> selectListNoticeByPreschoolCode(@Param("schCd")String schCd);
}
