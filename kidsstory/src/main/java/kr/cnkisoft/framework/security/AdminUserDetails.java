package kr.cnkisoft.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.cnkisoft.framework.enums.LoginUserType;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolVo;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;
import kr.cnkisoft.kidsstory.user.domain.UserVo;

public class AdminUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	
	String username;
	String password;
	LoginUserVo loginUserVo;
	
	protected AdminUserDetails(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		
		String schCd = "TEST1";
		String schNm = "테스트 유치원";
		if (username.equals("test02")) {
			schCd = "000001";
			schNm = "동화나라";
		}
		
		UserVo user = new UserVo();
		user.setUserNm("테스트관리자");
		user.setUserId(999);
		user.setUserType(LoginUserType.ADMIN.getCode());
		user.setUserId(3);
		user.setClsId(1);
		user.setSchCd(schCd);
		user.setUserType("TCH");
		user.setImgSrc("/file/data//prof/prof_4.png");
		
		PreschoolVo preschool = new PreschoolVo();
		preschool.setSchCd(schCd);
		preschool.setSchName(schNm);
		
		user.setPreschool(preschool);
		
		loginUserVo = new LoginUserVo(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ADMIN");
		authorities.add(roleUser);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public LoginUserVo getLoginUser() {
		return loginUserVo;
	}
}
