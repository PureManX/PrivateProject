package kr.cnkisoft.kidsstory.board.service;

import java.util.List;

import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineIndivualInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentHistDto;
import kr.cnkisoft.kidsstory.board.vo.BoardProcessParamVo;

/**
 * Created by PureMaN on 2017-08-27.
 */
public interface BoardLineDetailService {
	public void processBoarding(BoardProcessParamVo boardProcessParam);
	public void reserveUnboard(BoardLineStudentHistDto baordLineHist);
	public void cancelReserverUnboard(Integer lineHistId);
	public void sendPushToAllUsersInBusLine(Integer lineId, String histDate);
	public void startBoardService(Integer lineId);
	public void endBoardService(Integer lineId);
	public Integer getCurrentUserBusLineId();
	
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
