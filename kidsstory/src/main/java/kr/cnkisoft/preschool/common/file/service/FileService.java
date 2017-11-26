package kr.cnkisoft.preschool.common.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;

public interface FileService {
	public List<FileInfoDto> getFileListByCreator(Integer userId);
	public List<FileInfoDto> getFileListByClass(Integer classId);
	public FileInfoDto createFile(MultipartFile file, String filetype, Integer classId);
}
