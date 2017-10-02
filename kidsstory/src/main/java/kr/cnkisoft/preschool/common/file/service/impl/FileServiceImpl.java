package kr.cnkisoft.preschool.common.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.common.file.mapper.FileMapper;
import kr.cnkisoft.preschool.common.file.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileMapper fileMapper;
	
	@Override
	public List<FileInfoDto> getFileListByCreator(Integer userId) {
		return fileMapper.selectListImageFileByCreatedBy(userId);
	}

	@Override
	public List<FileInfoDto> getFileListByClass(Integer classId) {
		return fileMapper.selectListImageFileByClassId(classId);
	}

}
