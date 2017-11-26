package kr.cnkisoft.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.UserVo;

/**
 * Created by PureMaN on 2017-06-04.
 */
public class LoginUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	LoginUserVo loginUser;

	public LoginUserDetails(LoginUserVo loginUser) {
		if (loginUser.getUser() == null) {
			loginUser = new LoginUserVo();

			UserVo guestUser = new UserVo();
			guestUser.setUserNm("게스트");

			loginUser.setUser(guestUser);
		}
		this.loginUser = loginUser;


	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("USER");
		authorities.add(roleUser);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return loginUser.getUser().getUserNm();
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
		return loginUser;
	}
}
