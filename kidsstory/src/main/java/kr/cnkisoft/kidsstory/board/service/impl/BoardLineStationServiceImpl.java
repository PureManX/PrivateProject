package kr.cnkisoft.kidsstory.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.board.mapper.BoardLineStationMapper;
import kr.cnkisoft.kidsstory.board.service.BoardLineStationService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailDto;
import kr.cnkisoft.kidsstory.board.vo.BoardLineDetailVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineStudentRelationDto;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;

@Service
public class BoardLineStationServiceImpl implements BoardLineStationService {

	@Autowired
	BoardLineStationMapper boardLineStationMapper;

	@Override
	public List<BoardLineDetailVo> getBoardStationDetailListByLineId(Integer lineId) {
		return boardLineStationMapper.selectListLineDetailByLineId(lineId);
	}

	@Override
	public void createBoardStation(BoardLineDetailDto boardLineDetail) {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		boardLineDetail.setCreatedBy(loginUser.getLoginUserId());

		boardLineStationMapper.insertBoardLineDetail(boardLineDetail);
	}

	@Override
	public void modifyBoardStation(BoardLineDetailDto boardLineDetail) {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		boardLineDetail.setUpdatedBy(loginUser.getLoginUserId());

		boardLineStationMapper.updateBoardLineDetail(boardLineDetail);
	}

	@Override
	public void modifyBoardListStationStudendList(Integer lineDetailId, List<BoardLineStudentRelationDto> studentList) {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();

		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}

		// 입력 값이 빈값이면 모두 삭제
		if (studentList.isEmpty()) {
			boardLineStationMapper.deleteMapStationAllStudent(lineDetailId);
			return;
		}

		// 정류장 Id로 매핑 정보 조회
		List<BoardLineStudentRelationDto> prevStudentList = boardLineStationMapper.selectListMapStationAllStudent(lineDetailId);

		// 기존 매핑 정보가 존재 하지 않으면 모두 insert
		if (prevStudentList.isEmpty()) {
			for (BoardLineStudentRelationDto newStudent : studentList) {
				newStudent.setCreatedBy(loginUser.getLoginUserId());
				boardLineStationMapper.insertMapStationStudent(newStudent);
			}

			return;
		}

		// 파라미터 리스트 기준으로 루프 돌면서 존재 하지 않는 정보 insert
		for (BoardLineStudentRelationDto newStudent : studentList) {
			if (prevStudentList.stream().filter(x -> compareStationStudentRelation(x, newStudent)).count() == 0L) {
				newStudent.setCreatedBy(loginUser.getLoginUserId());
				boardLineStationMapper.insertMapStationStudent(newStudent);
			}
		}

		// 매핑정보 조회 기준으로 루프 돌면서 파라미터에 존재 하지 않는 정복 delete
		for (BoardLineStudentRelationDto oldStudent : prevStudentList) {
			if (studentList.stream().filter(x -> compareStationStudentRelation(x, oldStudent)).count() == 0L) {
				boardLineStationMapper.deleteMapStationStudent(oldStudent);
			}
		}
	}

	private boolean compareStationStudentRelation(BoardLineStudentRelationDto a, BoardLineStudentRelationDto b) {
		if (a == null || b == null) {
			return false;
		}

		return a.getLineDtlId().equals(b.getLineDtlId()) && a.getStduId().equals(b.getStduId());
	}
}
