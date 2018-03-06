package kr.cnkisoft.kidsstory.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;

@Mapper
public interface BoardLineMapper {
	
	// 노선이 현재 운행중인지 조회
	@Select("SELECT * FROM PRESCH_LINE_SERVICE WHERE LINE_ID = #{lineId} AND SERVICE_END_DT IS NULL "
           + " AND SERVICE_START_DT >= CURRENT_DATE"
           + " AND SERVICE_START_DT < adddate(CURRENT_DATE, 1)")
	public BoardLineServiceDto selectStartedBoardService(@Param("lineId")Integer lineId);	
	public BoardLineInfoVo selectBoardLineInfoByLineId(@Param("lineId")int lineId);
	public BoardLineInfoVo selectInProgressBoardLineInfoByLineId(@Param("lineId")int lineId);
	public List<BoardLineInfoVo> selectBoardLineListInfoByLineType(@Param("lineType")String lineType, @Param("schCd")String schCd);

}
