package kr.cnkisoft.preschool.push.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PreSchoolPushIdDto extends CommonDto{
	private String contact;
	private String deviceId;
	private String osType;
}
