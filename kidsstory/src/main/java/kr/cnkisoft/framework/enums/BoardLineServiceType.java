package kr.cnkisoft.framework.enums;

import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * Created by PureMaN on 2017-06-05.
 */
public enum BoardLineServiceType implements CodeEnum{
	READY("0", "대기")
	, START("10", "출발")
	, COMPETE("20", "도착")
	;

	String code;
	@Getter
	String value;

	BoardLineServiceType(String code, String value) {
		this.code = code;
		this.value = value;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}
	
    @MappedTypes(BoardLineServiceType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<BoardLineServiceType> {
        public TypeHandler() {
            super(BoardLineServiceType.class);
        }
    }
    
	public static BoardLineServiceType fromCode(String code) {
		BoardLineServiceType[] enums = BoardLineServiceType.values();
		
		for (BoardLineServiceType loginUserType : enums) {
			if (loginUserType.getCode().equals(code)) {
				return loginUserType;
			}
		}
		
		return null;
	}
}
