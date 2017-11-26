package kr.cnkisoft.preschool.common.file.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.preschool.common.constant.FileConstant;
import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.common.file.mapper.FileMapper;
import kr.cnkisoft.preschool.common.file.service.FileService;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	@Override
	public FileInfoDto createFile(MultipartFile file, String filetype, Integer classId) {
		// 로그인 유저 정보 
		LoginUserVo loginUser = AuthUtils.getLoginUser();
		
		// 학급 Id 체크 
		if (classId == null) {
			classId = loginUser.getUser().getClsId();
		}
		
		FileInfoDto fileInfo = new FileInfoDto();
		
		try {
			// 파일 Path 처리
			String homePath = Paths.get(FileConstant.getDataFolderPath(filetype.toUpperCase())).toString();
			String oriFileNm = file.getOriginalFilename();
			String ext = oriFileNm.substring(oriFileNm.lastIndexOf("."));
			String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
			
			fileInfo.setFileNm(fileName);
			fileInfo.setFileType(filetype.toUpperCase());
			fileInfo.setClsId(classId);
			fileInfo.setCreatedBy(loginUser.getLoginUserId());
			
			log.info("file upload info = {}", fileInfo);
			fileMapper.insertFileInfo(fileInfo);
			FileUtils.forceMkdir(new File(homePath));
			File dest = new File(homePath + "/" + fileName);
			file.transferTo(dest);
			
			log.info("file upload Success : {}", oriFileNm);
			fileInfo.setImgSrc(FileConstant.getFilePath(fileInfo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("파일 업로드에 실패했습니다.", e);
		}
		
		return fileInfo;
	}

}
