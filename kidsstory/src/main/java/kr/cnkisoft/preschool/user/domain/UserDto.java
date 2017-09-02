package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDto extends CommonDto {
	private Integer userId;
	private String userNm;
	private String userType;
	private Integer parUserId;
	private String contact;
	private String email;
	private String profImgId;
	private String sttusCd;
	private String schCd;
	private Integer clsId;
	private String fileNm;
	private String fileType;
	private String imgSrc;
}
