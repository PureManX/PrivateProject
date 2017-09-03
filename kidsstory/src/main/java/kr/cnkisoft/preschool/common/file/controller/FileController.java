package kr.cnkisoft.preschool.common.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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

import kr.cnkisoft.preschool.common.constant.FileConstant;
import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import kr.cnkisoft.preschool.common.file.mapper.FileMapper;

@Controller
public class FileController {

	@Autowired
	FileMapper fileMapper;
	
	@RequestMapping("/file/view/single")
	public String uploadView() {
		return "/views/file/uploadsingle";
	}
	
	@RequestMapping("/file/upload/single")
	@ResponseBody
	public Map<String, String> uploadSingle(
			@RequestParam MultipartFile file
			,@RequestParam(required=false) String filetype
			) {
		Map<String, String> result = new HashMap<String, String>();
		String homePath = FileConstant.getDataFolderPath(filetype.toUpperCase());
		String oriFileNm = file.getOriginalFilename();
		String ext = oriFileNm.substring(oriFileNm.lastIndexOf("."));
		String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
		
		FileInfoDto param = new FileInfoDto();
		param.setFileNm(fileName);
		param.setFileType(filetype.toUpperCase());
		param.setCreatedBy(3);
		
		try {
			fileMapper.insertFileInfo(param);
			
			FileUtils.forceMkdir(new File(homePath));
			File dest = new File(homePath + "/" + fileName);
			file.transferTo(dest);
			result.put("result", "success");
//			result.put("url", "http://cnkisoft.cafe24.com/preschool/images/" + file.getOriginalFilename());
		} catch (IllegalStateException e) {
			e.printStackTrace();
			result.put("result", "error");
		} catch (IOException e) {
			e.printStackTrace();
			result.put("result", "error");
		}
		
		return result;
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
	public List<FileInfoDto> fileListImage(@PathVariable String teacherId) {
		return fileMapper.selectListImageFileByTeacherId(teacherId);
	}
}
