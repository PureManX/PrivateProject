package kr.cnkisoft.kidsstory.board.service;

import java.util.List;

import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;

public interface BoardLineService {

	/**
	 * 운행 노선 기본 정보 조회 
	 * @param lineId
	 * @return
	 */
	public BoardLineInfoVo getBoardLineBasicInfo(Integer lineId);

	/**
	 * 현재 운행중인 노선 정보 조회 
	 * @param lineId
	 * @return
	 */
	public BoardLineInfoVo getInProgressBoardLineInfoByLineId(Integer lineId);

	/**
	 * 운행 상태 조회 
	 * @param lineId
	 * @return
	 */
	public BoardLineServiceDto getBoardServiceStatus(Integer lineId);

	/**
	 * 로그인 한 유저(선생님)의 유치원 운행 노선 정보 조회
	 * 
	 * @param lineType
	 * @return
	 */
	public List<BoardLineInfoVo> getBoardLineList(String lineType);
}
