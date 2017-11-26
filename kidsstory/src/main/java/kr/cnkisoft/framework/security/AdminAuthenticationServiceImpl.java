package kr.cnkisoft.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.cnkisoft.preschool.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by PureMaN on 2017-06-04.
 */
@Service("adminUserDetailsService")
@Slf4j
public class AdminAuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String userName = "admin";
		String password = bCryptPasswordEncoder.encode("admin");

		log.info(" == LOGIN USER :", username);
		AdminUserDetails userDetails = new AdminUserDetails(userName, password);
		
		return userDetails;
	}
	
}