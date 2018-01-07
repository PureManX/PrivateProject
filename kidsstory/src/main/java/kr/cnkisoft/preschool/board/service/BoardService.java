package kr.cnkisoft.preschool.board.service;

import java.util.List;

import kr.cnkisoft.preschool.board.vo.BoardLineDetailVo;
import kr.cnkisoft.preschool.board.vo.BoardLineIndivualInfoVo;
import kr.cnkisoft.preschool.board.vo.BoardLineInfoVo;
import kr.cnkisoft.preschool.board.vo.BoardLineServiceDto;
import kr.cnkisoft.preschool.board.vo.BoardLineStudentHistDto;
import kr.cnkisoft.preschool.board.vo.BoardProcessParamVo;

/**
 * Created by PureMaN on 2017-08-27.
 */
public interface BoardService {
	public void processBoarding(BoardProcessParamVo boardProcessParam);
	public void reserveUnboard(BoardLineStudentHistDto baordLineHist);
	public void cancelReserverUnboard(Integer lineHistId);
	public void sendPushToAllUsersInBusLine(Integer lineId, String histDate);
	public void startBoardService(Integer lineId);
	public Integer getCurrentUserBusLineId();
	public BoardLineInfoVo getBoardLineInfo(Integer lineId);
	public BoardLineServiceDto getBoardService(Integer lineId);
	
	/**
	 * 운행 노선 id를 이용하여 운행 상세 정보(노선 및 탑승 아이들 정보)를 조회
	 * @param lineId	운행노선 ID
	 * @param histDate	운행일자
	 * @return
	 */
	public List<BoardLineDetailVo> getBoardLineDetailList(Integer lineId, String histDate);
	
	/**
	 * 학생 id를 이용하여 상하차 노선 상세 정보 조회
	 * @param studentId
	 * @return
	 */
	public BoardLineIndivualInfoVo getBoardLineWithDetailByStudentId(Integer studentId);
}
