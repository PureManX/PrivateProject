package kr.cnkisoft.preschool.board.mapper;

import kr.cnkisoft.preschool.board.vo.BoardLineDto;
import kr.cnkisoft.preschool.board.vo.LineDetailVo;
import kr.cnkisoft.preschool.board.vo.LineHistVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {

	final String SELECT_LINE_DETAIL = 
			"SELECT b.LINE_DTL_ID, a.LINE_ID, d.PROF_IMG_ID, e.FILE_TYPE, e.FILE_NM, d.USER_NM, b.BOARD_TM, b.BOARD_LOC, b.BOARD_ORDER, c.BOARD_DIV, f.CLS_NM "
			+ "FROM PRESCH_LINE a"
			+ ",PRESCH_LINE_DTL b left join (SELECT * FROM PRESCH_LINE_HIST WHERE HIST_DATE = #{histDate}) c on b.LINE_DTL_ID = c.LINE_DTL_ID "
			+ ",USER_INFO d left join FILE_INFO e on d.PROF_IMG_ID = e.FILE_ID "
			+ ",USER_INFO g left join PRESCH_CLASS f on g.CLS_ID = f.CLS_ID "
			+ "WHERE 1=1 AND b.LINE_ID = a.LINE_ID AND b.STDU_ID = d.USER_ID AND d.USER_ID = g.USER_ID AND a.LINE_ID = #{lineId} "
			+ "ORDER BY b.BOARD_ORDER ASC";
	
	@Select(SELECT_LINE_DETAIL)
	public List<LineDetailVo> selectListLineDetail(@Param("lineId")int lineId, @Param("histDate")String histDate);
	
	@Insert("INSERT INTO PRESCH_LINE_HIST (LINE_DTL_ID, HIST_DATE, BOARD_DIV, UNB_REASON, CREATED_DT, CREATED_BY) VALUES(#{lineDtlId}, #{histDate}, #{boardDiv}, #{unbReason},now(), 'ADMIN');")
	public void insertBoardHist(LineHistVo param);
	
	@Select("SELECT a.*, b.BUS_NUM, c.USER_NM, CONCAT('/', LOWER(d.FILE_TYPE), '/', d.FILE_NM) AS IMG_SRC FROM PRESCH_LINE a, PRESCH_BUS b, USER_INFO c , FILE_INFO d "
			+ "WHERE a.BUS_ID = b.BUS_ID AND a.TEACHER_ID = c.USER_ID AND c.PROF_IMG_ID = d.FILE_ID AND a.LINE_ID = #{lineId}")
	public BoardLineDto selectBoardLineInfoByLineId(@Param("lineId")int lineId);


	@Select("SELECT a.*, b.BUS_NUM, c.USER_NM, CONCAT('/', LOWER(d.FILE_TYPE), '/', d.FILE_NM) AS IMG_SRC FROM PRESCH_LINE a, PRESCH_BUS b, USER_INFO c , FILE_INFO d "
			+ "WHERE a.BUS_ID = b.BUS_ID AND a.TEACHER_ID = c.USER_ID AND c.PROF_IMG_ID = d.FILE_ID AND a.LINE_TYPE = #{lineType}")
	public List<BoardLineDto> selectBoardLineListInfoByLineType(@Param("lineType")String lineType);


	@Select("SELECT b.LINE_DTL_ID FROM PRESCH_LINE a JOIN PRESCH_LINE_DTL b ON a.LINE_ID = b.LINE_ID WHERE a.LINE_TYPE = #{lineType} AND b.STDU_ID = #{studentId}")
	public Integer selectLineDetailIdByLoginParents(@Param("lineType")String lineType, @Param("studentId")int studentId);

	@Select("SELECT * FROM PRESCH_LINE_HIST WHERE LINE_DTL_ID = #{lineDtlId} AND BOARD_DIV = 'N'")
	public List<LineHistVo> selectListLineHist(@Param("lineDtlId")int lineDtlId);
	
	@Delete("DELETE FROM PRESCH_LINE_HIST WHERE LINE_DTL_ID In (SELECT LINE_DTL_ID FROM PRESCH_LINE_DTL WHERE LINE_ID = #{lineId})")
	public int deleteLineHistByLineId(@Param("lineId")int lineId);
}
