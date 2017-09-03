package kr.cnkisoft.preschool.common.domain;

import kr.cnkisoft.framework.enums.JsonResultStatus;
import lombok.Getter;
import lombok.Setter;


public class CommonResultVo<T> {
	@Getter
	@Setter
	private JsonResultStatus code = JsonResultStatus.SUCCESS;

	@Getter
	private T data;

	public String getMessage() {
		return code.getMessage();
	}

	public void setData(T data) {
		if (data == null) {
			code = JsonResultStatus.NODATA;
		}

		this.data = data;
	}
}
