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

import org.apache.ibatis.type.TypeException;

import java.beans.PropertyEditorSupport;

/**
 * Description
 * 
 * @author 박상현/SKTECH (sanghyun.park.tx@sk.com)
 * @data 2017. 4. 28.
 */
public class CodeEnumPropertyEditorSupport<E extends Enum <E>> extends PropertyEditorSupport {
	
	Class<E> type;
	
	public CodeEnumPropertyEditorSupport(Class<E> type) {
		this.type = type;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(getCodeEnum(text));
	}

	@Override
	public String getAsText() {
		return ((CodeEnum)this.getValue()).getCode();
	}
	
    private CodeEnum getCodeEnum(String code) {
        try {
            CodeEnum[] enumConstants = (CodeEnum[]) type.getEnumConstants();
            for (CodeEnum codeNum: enumConstants) {          
                if (codeNum.getCode().equals(code)) {
                    return codeNum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TypeException("Can't make enum object '" + type + "'", e);
        }
    }
}
