package kr.cnkisoft.kidsstory.board.service;

import java.util.List;

import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentRelationDto;

public interface BoardLineStationService {
	/**
	 * 운행 노선 id를 이용하여 정류장 상세 정보(노선 및 탑승 아이들 정보)를 조회
	 * @param lineId	운행노선 ID
	 * @return
	 */
	public List<BoardLineDetailVo> getBoardStationDetailListByLineId(Integer lineId);
	public void createBoardStation(BoardLineDetailDto boardLineDetail);
	public void modifyBoardStation(BoardLineDetailDto boardLineDetail);
	public void modifyBoardListStationStudendList(Integer lineDetailId, List<BoardLineStudentRelationDto> studentList);
}
