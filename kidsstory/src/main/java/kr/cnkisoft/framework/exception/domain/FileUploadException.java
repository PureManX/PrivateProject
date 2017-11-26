package kr.cnkisoft.framework.exception.domain;

/**
 * FileUpload Exception Class
 * 
 * @author PureMaN
 *
 */
public class FileUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected FileUploadException() {
		super();
	}

	protected FileUploadException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	protected FileUploadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	protected FileUploadException(String arg0) {
		super(arg0);
	}

	protected FileUploadException(Throwable arg0) {
		super(arg0);
	}
	
}
