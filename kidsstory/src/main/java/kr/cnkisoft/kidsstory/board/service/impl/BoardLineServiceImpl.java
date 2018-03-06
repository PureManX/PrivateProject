package kr.cnkisoft.kidsstory.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.board.mapper.BoardLineMapper;
import kr.cnkisoft.kidsstory.board.service.BoardLineService;
import kr.cnkisoft.kidsstory.board.vo.BoardLineInfoVo;
import kr.cnkisoft.kidsstory.board.vo.BoardLineServiceDto;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;

@Service
public class BoardLineServiceImpl implements BoardLineService {

	@Autowired
	BoardLineMapper boardLineMapper;
	
	@Override
	public BoardLineInfoVo getBoardLineBasicInfo(Integer lineId) {
		return boardLineMapper.selectBoardLineInfoByLineId(lineId);
	}

	@Override
	public BoardLineServiceDto getBoardServiceStatus(Integer lineId) {
		return boardLineMapper.selectStartedBoardService(lineId);
	}
	
	@Override
	public List<BoardLineInfoVo> getBoardLineList(String lineType) {
		UserVo user = AuthUtils.getLoginUser().getUser();

		String prescoolCode = user.getPreschool().getSchCd();

		List<BoardLineInfoVo> busLineList = boardLineMapper.selectBoardLineListInfoByLineType(lineType, prescoolCode);

		return busLineList;
	}

	@Override
	public BoardLineInfoVo getInProgressBoardLineInfoByLineId(Integer lineId) {
		return boardLineMapper.selectInProgressBoardLineInfoByLineId(lineId);
	}

	@Override
	public List<BoardLineInfoVo> getBoardLineListByCurretLoginPreshcoolCode() {
		LoginUserVo loginUser =  AuthUtils.getLoginUser();
		
		if (loginUser == null) {
			throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
		}
		
		return boardLineMapper.selectListBoardLineInfoByPreschoolCode(loginUser.getSchool().getSchCd());
	}

}
