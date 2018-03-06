package kr.cnkisoft.kidsstory.common.file.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.cnkisoft.kidsstory.common.constant.FileConstant;
import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.common.file.domain.FileInfoDto;
import kr.cnkisoft.kidsstory.common.file.mapper.FileMapper;
import kr.cnkisoft.kidsstory.common.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileController {

	@Autowired
	FileMapper fileMapper;
	
	@Autowired
	FileService fileService;
	
	@RequestMapping("/file/view/single")
	public String uploadView() {
		return "/views/file/uploadsingle";
	}
	
	@RequestMapping("/file/upload/single")
	@ResponseBody
	public CommonResultVo uploadSingle(
			@RequestParam MultipartFile file
			, @RequestParam(required=false) String filetype
			, @RequestParam(required=false) Integer classId
			) {
		FileInfoDto uploadFileInfo = fileService.createFile(file, filetype, classId);
		
		return new CommonResultVo(uploadFileInfo);
	}
	
	@RequestMapping(value="/file/data/{type}/{filename:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<byte[]> getImages(@PathVariable String filename, @PathVariable String type) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		String homePath = FileConstant.getDataFolderPath(type.toUpperCase());
		try {
			byte[] media = Files.readAllBytes(Paths.get(homePath + "/" + filename));

			ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);

			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(null, headers, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/file/list/image/{teacherId}")
	@ResponseBody
	public List<FileInfoDto> fileListImage(@PathVariable Integer teacherId) {
		return fileMapper.selectListImageFileByCreatedBy(teacherId);
	}
}
