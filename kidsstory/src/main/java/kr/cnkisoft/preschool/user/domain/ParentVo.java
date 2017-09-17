package kr.cnkisoft.preschool.user.domain;

import java.util.List;

import org.springframework.beans.BeanUtils;

import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParentVo extends UserVo {

	protected PreSchoolPushIdDto pushInfo;
	protected List<StudentVo> children;

	public ParentVo(UserDto userDto) {
		BeanUtils.copyProperties(userDto, this);
	}

	public ParentVo(Integer userId) {
		super.userId = userId;
	}
	
	public ParentVo(Integer userId, String userNm, String userType, String contact, Integer clsId, String email, String sttusCd, String imgSrc) {
		super.userId = userId;
		super.userNm = userNm;
		super.userType = userType;
		super.contact = contact;
		super.email = email;
		super.sttusCd = sttusCd;
		super.clsId = clsId;
		super.imgSrc = imgSrc;
	}
}
