package kr.cnkisoft.preschool.common.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.framework.utils.ImageUtils;
import kr.cnkisoft.preschool.common.constant.FileConstant;
import kr.cnkisoft.preschool.common.file.domain.DailyGalleryListVo;
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
			String fileName = UUID.randomUUID().toString().replace("-", "");
			String thumbNailName = fileName + "_thumb";
			
			fileInfo.setFileNm(fileName + ".PNG");
			fileInfo.setFileType(filetype.toUpperCase());
			fileInfo.setClsId(classId);
			fileInfo.setCreatedBy(loginUser.getLoginUserId());
			
			log.info("file upload info = {}", fileInfo);
			fileMapper.insertFileInfo(fileInfo);
			FileUtils.forceMkdir(new File(homePath));
			File dest = new File(homePath + "/" + fileName + ".PNG");
			File destThumb = new File(homePath + "/" + thumbNailName + ".PNG");
			
			makeOriginalImage(file, dest);
			makeThumbNailImage(file, destThumb);
			
			log.info("file upload Success : {}", oriFileNm);
			fileInfo.setImgSrc(FileConstant.getFilePath(fileInfo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("파일 업로드에 실패했습니다.", e);
		}
		
		return fileInfo;
	}


	@Override
	public FileInfoDto createProfileImage(MultipartFile file) {
		// 로그인 유저 정보 
		LoginUserVo loginUser = AuthUtils.getLoginUser();
		String filetype = "PROF";
		FileInfoDto fileInfo = new FileInfoDto();
		
		try {
			// 파일 Path 처리
			String homePath = Paths.get(FileConstant.getDataFolderPath(filetype)).toString();
			String oriFileNm = file.getOriginalFilename();
			String ext = oriFileNm.substring(oriFileNm.lastIndexOf("."));
			String fileName = UUID.randomUUID().toString().replace("-", "");
			
			fileInfo.setFileNm(fileName + ".PNG");
			fileInfo.setFileType(filetype.toUpperCase());
			fileInfo.setClsId(null);
			fileInfo.setCreatedBy(loginUser.getLoginUserId());
			
			log.info("profile upload info = {}", fileInfo);
			fileMapper.insertFileInfo(fileInfo);
			FileUtils.forceMkdir(new File(homePath));
			File dest = new File(homePath + "/" + fileName + ".PNG");
			
			makeThumbNailImage(file, dest);
			
			log.info("file upload Success : {}", oriFileNm);
			fileInfo.setImgSrc(FileConstant.getFilePath(fileInfo));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("파일 업로드에 실패했습니다.", e);
		}
		
		return fileInfo;
	}
	
	@Override
	public List<DailyGalleryListVo> getDailyGalleryListByClass(Integer classId) {
		int count = 5;
		List<DailyGalleryListVo> resulList = new ArrayList<>();
		List<DailyGalleryListVo> dailyGallerList = fileMapper.selectListDailyImageByClassId(classId);
		
		for (DailyGalleryListVo dailyGalleryListVo : dailyGallerList) {
			if (count > 0) {
				resulList.add(dailyGalleryListVo);
				count--;
			} else {
				break;
			}
		}
		
		return resulList;
	}
	
	private boolean makeThumbNailImage(MultipartFile uploadFile, File file) {
		BufferedImage image;
		boolean result;
		
		try {
			image = ImageUtils.resizeImage(ImageIO.read(uploadFile.getInputStream()), 256, 192);
			result = ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
			
			result = false;
		}
		
		return result;
	}

	private boolean makeOriginalImage(MultipartFile uploadFile, File file) {
		BufferedImage image;
		boolean result;
		
		try {
			image = ImageUtils.resizeImage(ImageIO.read(uploadFile.getInputStream()), 2048, 1536);
			result = ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
			
			result = false;
		}
		
		return result;
	}

}
