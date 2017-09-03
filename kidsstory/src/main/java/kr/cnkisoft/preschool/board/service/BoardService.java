package kr.cnkisoft.preschool.board.service;

import kr.cnkisoft.preschool.board.vo.BoardLineHistDto;
import kr.cnkisoft.preschool.board.vo.BoardLineInfoVo;
import kr.cnkisoft.preschool.board.vo.BoardLineServiceDto;

/**
 * Created by PureMaN on 2017-08-27.
 */
public interface BoardService {
	public void processBoarding(BoardLineHistDto baordLineHist);
	public void reserveUnboard(BoardLineHistDto baordLineHist);
	public void cancelReserverUnboard(String lineHistId);
	public void sendPushToAllUsersInBusLine(Integer lineId, String histDate);
	public void startBoardService(Integer lineId);
	public Integer getCurrentUserBusLineId();
	public BoardLineInfoVo getBoardLineInfo(Integer lineId);
	public BoardLineServiceDto getBoardService(Integer lineId);
}
