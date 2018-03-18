package kr.cnkisoft.kidsstory.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentRelationDto;

@Mapper
public interface BoardLineStationMapper {
	public List<BoardLineDetailVo> selectListLineDetail(@Param("lineId")Integer lineId, @Param("histDate")String histDate);
	public List<BoardLineDetailVo> selectListLineDetailByLineId(@Param("lineId")Integer lineId);
	public int insertBoardLineDetail(BoardLineDetailDto boardLineDetail);
	public int updateBoardLineDetail(BoardLineDetailDto boardLineDetail);

	public List<BoardLineStudentRelationDto> selectListMapStationAllStudent(@Param("lineDtlId")Integer lineDtlId);
	public int insertMapStationStudent(BoardLineStudentRelationDto boardLineStudentRelation);
	public int deleteMapStationStudent(BoardLineStudentRelationDto boardLineStudentRelation);
	public int deleteMapStationAllStudent(@Param("lineDtlId")Integer lineDtlId);
}
