package kr.cnkisoft.framework.enums;

import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * Created by PureMaN on 2017-06-05.
 */
public enum MenuType implements CodeEnum{
	MORING_SNACK("0", "오전 간식")
	, TEACHER("1", "점심")
	, AFTERNOON_SNACK("2", "오후 간식")
	;

	String code;
	@Getter
	String name;

	MenuType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}
	
    @MappedTypes(MenuType.class)
    public static class TypeHandler extends CodeEnumTypeHandler<MenuType> {
        public TypeHandler() {
            super(MenuType.class);
        }
    }
    
	public static MenuType fromCode(String code) {
		MenuType[] enums = MenuType.values();
		
		for (MenuType loginUserType : enums) {
			if (loginUserType.getCode().equals(code)) {
				return loginUserType;
			}
		}
		
		return null;
	}
}
