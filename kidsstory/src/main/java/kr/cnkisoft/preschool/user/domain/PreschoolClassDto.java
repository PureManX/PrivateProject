package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolClassDto extends CommonDto {
	private Integer clsId;
	private String clsNm;
 	private Integer hrTeacherId;
 	private String schSttusCd;
}
