package kr.cnkisoft.preschool.user.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
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
	
	public UserDto(Integer userId, String userNm, String userType, Integer parUserId, String contact, String email,
			Integer profImgId, String sttusCd, String schCd, Integer clsId, String fileNm, String fileType,
			String imgSrc) {
		this.userId = userId;
		this.userNm = userNm;
		this.userType = userType;
		this.parUserId = parUserId;
		this.contact = contact;
		this.email = email;
		this.profImgId = profImgId;
		this.sttusCd = sttusCd;
		this.schCd = schCd;
		this.clsId = clsId;
		this.fileNm = fileNm;
		this.fileType = fileType;
		this.imgSrc = imgSrc;
	}
}
