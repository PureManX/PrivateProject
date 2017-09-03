package kr.cnkisoft.framework.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by PureMaN on 2017-06-05.
 */
public enum JsonResultStatus implements CodeEnum{
	SUCCESS(10, "0", "Success")
	, NODATA(20, "100", "No Data")
	, ERROR(99, "500", "Server Error")
	;

	int value;
	String code;
	String message;

	JsonResultStatus(int value, String code, String message) {
		this.value = value;
		this.code = code;
		this.message = message;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
}
