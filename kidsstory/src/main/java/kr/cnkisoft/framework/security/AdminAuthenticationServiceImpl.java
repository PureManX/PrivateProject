package kr.cnkisoft.framework.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.cnkisoft.kidsstory.admin.domain.AdminUserVo;
import kr.cnkisoft.kidsstory.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by PureMaN on 2017-06-04.
 */
@Service("adminUserDetailsService")
@Slf4j
public class AdminAuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	AdminUserService adminUserService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info(" == LOGIN USER : {}", username);
		AdminUserVo adminUser = adminUserService.getAdminUserByLoginId(username);

		if (adminUser == null) {
			log.warn("{} 관리자가 존재 하지 않습니다.", username);
			throw new UsernameNotFoundException(username + " 관리자가 존재하지 않습니다.");
		}
		AdminUserDetails userDetails = new AdminUserDetails(adminUser);

		return userDetails;
	}

	@Test
	public void cryptTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		System.out.println(encoder.encode("admin01!"));
	}
}