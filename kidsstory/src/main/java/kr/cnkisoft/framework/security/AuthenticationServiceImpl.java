package kr.cnkisoft.framework.security;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.UserDto;
import kr.cnkisoft.preschool.user.mapper.UserMapper;
import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by PureMaN on 2017-06-04.
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {
		LoginUserVo user = userService.getLoginUser(contact);

		log.info(" == LOGIN USER :", user.toString());
		LoginUserDetails userDetails = new LoginUserDetails(user);
		return userDetails;
	}
}
