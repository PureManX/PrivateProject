package kr.cnkisoft.framework.common;

import kr.cnkisoft.framework.security.AuthUtils;
import kr.cnkisoft.kidsstory.user.domain.LoginUserVo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by PureMaN on 2017-06-06.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute
	public void addLoginUserAttributes(Model model) {
		LoginUserVo loginUser = AuthUtils.getLoginUser();
		model.addAttribute("loginUser", loginUser);
	}
}

