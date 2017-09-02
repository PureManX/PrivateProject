package kr.cnkisoft.framework.security;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Created by PureMaN on 2017-06-05.
 */
public class AuthUtils {
	public static LoginUserVo getLoginUser() {
		LoginUserVo loginUser = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			LoginUserDetails userDetails = (LoginUserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			loginUser = userDetails.getLoginUser();
		}

		return loginUser;
	}

	public static Integer getLoginUserId() {
		return AuthUtils.getLoginUser().getLoginUserId();
	}
}
