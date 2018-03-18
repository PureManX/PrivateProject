package kr.cnkisoft.kidsstory.user.domain;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends CommonDto {
	protected Integer userId;
	protected String userNm;
	protected String userType;
	protected Integer parUserId;
	protected String contact;
	protected String email;
	protected Integer profImgId;
	protected String sttusCd;
	protected String schCd;
	protected Integer clsId;
	protected String fileNm;
	protected String fileType;
	protected String imgSrc;

	public UserDto(Integer userId) {
		this.userId = userId;
	}
}
