package kr.cnkisoft.preschool.common.domain;

import kr.cnkisoft.framework.enums.JsonResultStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class CommonResultVo {
	@Getter
	@Setter
	private JsonResultStatus code = JsonResultStatus.SUCCESS;

	@Getter
	private Object data;

	public CommonResultVo(Object data) {
		setData(data);
	}
	
	public String getMessage() {
		return code.getMessage();
	}

	public void setData(Object data) {
		if (data == null) {
			code = JsonResultStatus.NODATA;
		}

		this.data = data;
	}

}
