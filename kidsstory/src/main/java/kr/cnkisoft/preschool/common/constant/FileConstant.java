package kr.cnkisoft.preschool.common.constant;

public class FileConstant {
	private static final String DATA_HOME_PATH = "/home/hosting_users/cnkisoft/tomcat/data";
	private static final String DATA_FOLDER_IMAGE = "/image";
	private static final String DATA_FOLDER_PROF = "/prof";
	private static final String DATA_FOLDER_MENU = "/menu";
	
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
}
