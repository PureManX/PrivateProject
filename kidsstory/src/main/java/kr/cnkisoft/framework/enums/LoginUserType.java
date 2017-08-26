package kr.cnkisoft.framework.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by PureMaN on 2017-06-05.
 */
public enum LoginUserType implements CodeEnum{
	PARENT(10, "PAR")
	, TEACHER(20, "TCH")
	, GUEST(99, "GST")
	;

	int value;
	String code;

	LoginUserType(int value, String code) {
		this.value = value;
		this.code = code;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

	public int getValue() {
		return value;
	}
}
