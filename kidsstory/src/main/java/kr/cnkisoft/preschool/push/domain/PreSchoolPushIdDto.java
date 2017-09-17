package kr.cnkisoft.preschool.push.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PreSchoolPushIdDto extends CommonDto{
	private String contact;
	private String deviceId;
	private String osType;
	
	public PreSchoolPushIdDto(String contact, String deviceId, String osType) {
		super();
		this.contact = contact;
		this.deviceId = deviceId;
		this.osType = osType;
	}
}
