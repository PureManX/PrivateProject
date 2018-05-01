package kr.cnkisoft.kidsstory.admin.domain;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class AdminUserVo extends CommonDto{
	private Integer adminId;
	private String loginId;
	private String loginPassword;
	private String schCd;
	private Integer userId;

	private UserVo user;
	private PreschoolVo school;
	private List<SimpleGrantedAuthority> roles;
}
