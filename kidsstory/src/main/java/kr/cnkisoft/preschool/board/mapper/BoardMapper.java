package kr.cnkisoft.preschool.board.mapper;

import kr.cnkisoft.preschool.board.vo.*;
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
	public void insertBoardHist(BoardLineHistDto param);
	
	@Select("SELECT a.*, b.BUS_NUM, c.USER_NM, CONCAT('/', LOWER(d.FILE_TYPE), '/', d.FILE_NM) AS IMG_SRC FROM PRESCH_LINE a, PRESCH_BUS b, USER_INFO c , FILE_INFO d "
			+ "WHERE a.BUS_ID = b.BUS_ID AND a.TEACHER_ID = c.USER_ID AND c.PROF_IMG_ID = d.FILE_ID AND a.LINE_ID = #{lineId}")
	public BoardLineDto selectBoardLineInfoByLineId(@Param("lineId")int lineId);


	@Select("SELECT a.*, b.BUS_NUM, c.USER_NM, CONCAT('/', LOWER(d.FILE_TYPE), '/', d.FILE_NM) AS IMG_SRC FROM PRESCH_LINE a, PRESCH_BUS b, USER_INFO c , FILE_INFO d "
			+ "WHERE a.BUS_ID = b.BUS_ID AND a.TEACHER_ID = c.USER_ID AND c.PROF_IMG_ID = d.FILE_ID AND a.LINE_TYPE = #{lineType} AND a.SCH_CD = #{schCd}")
	public List<BoardLineDto> selectBoardLineListInfoByLineType(@Param("lineType")String lineType, @Param("schCd")String schCd);


	@Select("SELECT b.LINE_DTL_ID FROM PRESCH_LINE a JOIN PRESCH_LINE_DTL b ON a.LINE_ID = b.LINE_ID WHERE a.LINE_TYPE = #{lineType} AND b.STDU_ID = #{studentId}")
	public Integer selectLineDetailIdByLoginParents(@Param("lineType")String lineType, @Param("studentId")int studentId);

	@Select("SELECT * FROM PRESCH_LINE_HIST WHERE LINE_DTL_ID = #{lineDtlId} AND BOARD_DIV = 'N'")
	public List<BoardLineHistDto> selectListLineHist(@Param("lineDtlId")int lineDtlId);
	
	@Delete("DELETE FROM PRESCH_LINE_HIST WHERE LINE_DTL_ID In (SELECT LINE_DTL_ID FROM PRESCH_LINE_DTL WHERE LINE_ID = #{lineId})")
	public int deleteLineHistByLineId(@Param("lineId")int lineId);


	// 버스 노선의 현재 날짜 미탑승 리스트 추출
	@Select("SELECT detail.* FROM PRESCH_LINE_DTL detail" +
			" LEFT JOIN PRESCH_LINE_HIST hist ON detail.LINE_DTL_ID = hist.LINE_DTL_ID AND hist.CREATED_DT >= CURRENT_DATE AND hist.CREATED_DT < adddate(CURRENT_DATE, 1)" +
			" WHERE detail.LINE_ID = #{lineId} AND hist.LINE_HIST_ID IS NULL" +
			" ORDER BY BOARD_ORDER")
	public List<BoardLineDetailDto> selectListNonBoardingListByLineId(@Param("lineId")Integer lineId);


	@Insert("INSERT INTO PRESCH_LINE_SERVICE (LINE_ID, SERVICE_TEACHER_ID, SERVICE_START_DT, SERVICE_END_DT, CREATED_DT, CREATED_BY) " +
			"VALUES (#{lineId}, #{serviceTeacherId}, #{serviceStartDt}, null, NOW(), #{createdBy})")
	public int insertBoardService(BoardLineServiceDto boardLineService);

	// 노선 정보 조회
	@Select("SELECT * FROM PRESCH_LINE WHERE LINE_ID = #{lineId}")
	public BoardLineDto selectBoardLine(@Param("lineId")Integer lineId);

	// 버스 정보 조회
	@Select("SELECT * FROM PRESCH_BUS WHERE BUS_ID = #{busId}")
	public PreschoolBusDto selectPreschoolBus(@Param("busId")Integer busId);

	// 현재 출발한 버스 정보 조회 (학부모)
	@Select("SELECT line.LINE_ID FROM PRESCH_LINE_DTL linedetail" +
			"    JOIN PRESCH_LINE line ON linedetail.LINE_ID = line.LINE_ID" +
			"    JOIN PRESCH_LINE_SERVICE service" +
			"        ON line.LINE_ID = service.LINE_ID" +
			"           AND service.SERVICE_START_DT >= CURRENT_DATE" +
			"           AND service.SERVICE_START_DT < adddate(CURRENT_DATE, 1)" +
			"           AND SERVICE_END_DT IS NULL" +
			" WHERE linedetail.STDU_ID = #{childId} ")
	public List<Integer> selectLineIdInBoardService(@Param("childId")Integer childId);

}
