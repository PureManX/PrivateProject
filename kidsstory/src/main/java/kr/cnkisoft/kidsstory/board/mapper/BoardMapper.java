package kr.cnkisoft.kidsstory.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineWithDetailVo;
import kr.cnkisoft.kidsstory.board.vo.PreschoolBusDto;

@Mapper
public interface BoardMapper {

	final String SELECT_LINE_DETAIL = 
			"SELECT b.LINE_DTL_ID, a.LINE_ID, d.PROF_IMG_ID, e.FILE_TYPE, e.FILE_NM, d.USER_NM, b.BOARD_TM, b.BOARD_LOC, b.BOARD_ORDER, c.BOARD_DIV, f.CLS_NM "
			+ "FROM PRESCH_LINE a"
			+ ",PRESCH_LINE_DTL b left join (SELECT * FROM PRESCH_LINE_HIST WHERE HIST_DATE = #{histDate}) c on b.LINE_DTL_ID = c.LINE_DTL_ID "
			+ ",USER_INFO d left join FILE_INFO e on d.PROF_IMG_ID = e.FILE_ID "
			+ ",USER_INFO g left join PRESCH_CLASS f on g.CLS_ID = f.CLS_ID "
			+ "WHERE 1=1 AND b.LINE_ID = a.LINE_ID AND b.STDU_ID = d.USER_ID AND d.USER_ID = g.USER_ID AND a.LINE_ID = #{lineId} "
			+ "ORDER BY b.BOARD_ORDER ASC";

	public List<BoardLineDetailVo> selectListLineDetail(@Param("lineId")int lineId, @Param("histDate")String histDate);
	
	public int insertBoardDetailHist(BoardLineDetailHistDto param);
	
	public int insertBoardDetailStudentHist(BoardLineStudentHistDto param);

	@Select("SELECT b.LINE_DTL_ID FROM PRESCH_LINE a JOIN PRESCH_LINE_DTL b ON a.LINE_ID = b.LINE_ID WHERE a.LINE_TYPE = #{lineType} AND b.STDU_ID = #{studentId}")
	public Integer selectLineDetailIdByLoginParents(@Param("lineType")String lineType, @Param("studentId")int studentId);

	@Select("SELECT * FROM PRESCH_LINE_STDU_HIST WHERE LINE_DTL_ID = #{lineDtlId} AND BOARD_DIV = 'R' AND USER_ID = #{stduentId}")
	public List<BoardLineStudentHistDto> selectListLineHist(@Param("lineDtlId")Integer lineDtlId, @Param("stduentId")Integer stduentId);
	
	@Delete("DELETE FROM PRESCH_LINE_DTL_HIST WHERE LINE_DTL_ID In (SELECT LINE_DTL_ID FROM PRESCH_LINE_DTL WHERE LINE_ID = #{lineId}) AND HIST_DATE = #{histDate}")
	public int deleteLineDetailHistByLineId(@Param("lineId")Integer lineId, @Param("histDate")String histDate);

	@Delete("DELETE FROM PRESCH_LINE_STDU_HIST WHERE LINE_DTL_ID In (SELECT LINE_DTL_ID FROM PRESCH_LINE_DTL WHERE LINE_ID = #{lineId}) AND HIST_DATE = #{histDate}")
	public int deleteLineDetailStudentHistByLineId(@Param("lineId")Integer lineId, @Param("histDate")String histDate);

	// 버스 노선의 현재 날짜 미탑승 리스트 추출
//	@Select("SELECT detail.* FROM PRESCH_LINE_DTL detail" +
//			" LEFT JOIN PRESCH_LINE_HIST hist ON detail.LINE_DTL_ID = hist.LINE_DTL_ID AND hist.HIST_DATE = #{histDate} " +
//			" WHERE detail.LINE_ID = #{lineId} AND hist.LINE_HIST_ID IS NULL" +
//			" ORDER BY BOARD_ORDER")
	public List<BoardLineDetailDto> selectListNonBoardingListByLineId(@Param("lineId")Integer lineId, @Param("histDate")String histDate);
	
	// 버스 노선 완료 대상 리스트 추출
	public List<BoardLineDetailDto> selectListBoardingCompleteListByLineId(@Param("lineId")Integer lineId, @Param("histDate")String histDate);


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
	
	// 운행 기록 정보 삭제 
	@Delete("DELETE FROM PRESCH_LINE_STDU_HIST WHERE LINE_DTL_STDU_HIST_ID = #{lineHistId}")
	public int deleteBoardHist(@Param("lineHistId")Integer lineHistId);

	
	// 현재 운행중인 노선 취소 처리 
	@Delete("DELETE FROM PRESCH_LINE_SERVICE WHERE LINE_ID = #{lineId} "
           + " AND SERVICE_START_DT >= CURRENT_DATE"
           + " AND SERVICE_START_DT < adddate(CURRENT_DATE, 1)")
	public int deleteBoardService(@Param("lineId")Integer lineId);
	
	public BoardLineWithDetailVo selectAttBoardLineDetailByStudentId(@Param("studentId")Integer studentId);
	public BoardLineWithDetailVo selectComBoardLineDetailByStudentId(@Param("studentId")Integer studentId);
	
	// 운행중인 버스 노선 조회
	public BoardLineServiceDto seelctInProgressBoardServiceByLineId(@Param("lineId")Integer lineId);
	
	// 운행중인 버스 노선 종료 처리
	public int updateBoardServiceEndDate(@Param("lineId")Integer lineId);
}
