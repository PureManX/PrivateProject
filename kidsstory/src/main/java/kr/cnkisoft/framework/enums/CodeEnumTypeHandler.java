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

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description
 * //stunstun.tistory.com/247 [holaxprogramming.com]
 * 
 * @author 박상현/SKTECH (sanghyun.park.tx@sk.com)
 * @data 2017. 4. 28.
 */
public abstract class CodeEnumTypeHandler <E extends Enum <E>> implements TypeHandler <CodeEnum> {
	 
    private Class <E> type;
 
    public CodeEnumTypeHandler(Class <E> type) {
        this.type = type;
    }
 
    @Override
    public void setParameter(PreparedStatement ps, int i, CodeEnum parameter, JdbcType jdbcType) throws SQLException {
    	if (parameter != null) {
    		ps.setString(i, parameter.getCode());
    	} else {
    		ps.setString(i, null);
    	}
    	
    }
 
    @Override
    public CodeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnum(code);
    }
 
    @Override
    public CodeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnum(code);
    }
 
    @Override
    public CodeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnum(code);
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