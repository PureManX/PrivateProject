package kr.cnkisoft.framework.security;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by PureMaN on 2017-06-04.
 */
public class LoginUserDetails implements UserDetails{

	LoginUserVo loginUser;

	public LoginUserDetails(LoginUserVo loginUser) {
		if (loginUser.getUser() == null) {
//			loginUser = new LoginUserVo();
			UserDto guestUser = new UserDto();
			guestUser.setUserNm("게스트");

			loginUser.setUser(guestUser);
		}
		this.loginUser = loginUser;


	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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
