/*
 *
 * Copyright (c) 2017 SK TECHX.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK TECHX.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK TECHX.
 *
 */
package kr.cnkisoft.framework.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Description
 * 
 * @author 박상현/SKTECH (sanghyun.park.tx@sk.com)
 * @data 2017. 5. 4.
 */
public enum CommonResponseCode implements CodeEnum {
	SUCCESS(0, "0")
	, NO_DATA(100, "100")
	;

	private int value;
	private String code;

	CommonResponseCode(int value, String code) {
		this.value = value;
		this.code = code;
	}
	
	@JsonValue
	public int getValue() {
		return value;
	}

	@Override
	public String getCode() {
		return code;
	}
}
