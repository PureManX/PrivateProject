package kr.cnkisoft.framework.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;

/**
 * Created by PureMaN on 2017-06-05.
 */
public class AuthUtils {
	public static LoginUserVo getLoginUser() {
		LoginUserVo loginUser = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
			UserDetails userDetails = (UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			
			if (userDetails instanceof AdminUserDetails) {
				AdminUserDetails adminUserDetails = (AdminUserDetails)userDetails;
				loginUser = adminUserDetails.getLoginUser();
			} else if (userDetails instanceof LoginUserDetails) {
				LoginUserDetails loginUserDetails = (LoginUserDetails)userDetails;
				loginUser = loginUserDetails.getLoginUser();
			}
		}

		return loginUser;
	}

	public static Integer getLoginUserId() {
		return AuthUtils.getLoginUser().getLoginUserId();
	}
}
