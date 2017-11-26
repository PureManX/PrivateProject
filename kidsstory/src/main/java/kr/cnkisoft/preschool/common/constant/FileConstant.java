package kr.cnkisoft.preschool.common.constant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileConstant {
	private static final String DATA_FOLDER_IMAGE = "/image";
	private static final String DATA_FOLDER_PROF = "/prof";
	private static final String DATA_FOLDER_MENU = "/menu";
	private static String DATA_HOME_PATH = "/home/hosting_users/cnkisoft/tomcat/data";
	public static final String DATA_URL_PATH = "/file/data/{type}/{filename}";
	
	@Value("${file.home.path}")
	private String propertyPath;
	
	@PostConstruct
	public void init() {
		log.info("home-path : {}", propertyPath);
		DATA_HOME_PATH = propertyPath;
	}
	
	enum Folder {
		IMAGE(DATA_FOLDER_IMAGE), PROF(DATA_FOLDER_PROF) ,MENU(DATA_FOLDER_MENU);
		
		private String folder;
		
		private Folder(String folder) {
			this.folder = folder;
		}
		
		public String getFolder() {
			return this.folder;
		}
	}
	
	public static String getDataFolderPath(String type) {
		return DATA_HOME_PATH + Folder.valueOf(type).getFolder(); 
	}
	
	public static String getFilePath(FileInfoDto fileInfo) {
		return DATA_URL_PATH.replace("{type}", fileInfo.getFileType().toLowerCase())
				.replace("{filename}", fileInfo.getFileNm());
	}
}
