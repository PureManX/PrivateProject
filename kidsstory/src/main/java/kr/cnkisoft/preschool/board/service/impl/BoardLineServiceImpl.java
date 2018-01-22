package kr.cnkisoft.preschool.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.board.mapper.BoardLineMapper;
import kr.cnkisoft.preschool.board.service.BoardLineService;
import kr.cnkisoft.preschool.board.vo.BoardLineInfoVo;
import kr.cnkisoft.preschool.board.vo.BoardLineServiceDto;
import kr.cnkisoft.preschool.user.domain.UserVo;

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

}
