package kr.cnkisoft.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import kr.cnkisoft.framework.filter.CookieAuthenticationFilter;
import kr.cnkisoft.framework.utils.HttpSupportUtils;

/**
 * Created by PureMaN on 2017-05-10.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	public static final String[] SECURITY_URL_PATTERNS = {"/css/**", "/images/**", "/file/**", "/adminlte/**",  "/js/**", "/admin/**", "/rest/admin/**"};
	
	@Autowired
	@Qualifier("cookieUserDetailsService")
	UserDetailsService cookieUserDetailsService;

	@Autowired
	@Qualifier("adminUserDetailsService")
	UserDetailsService adminUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/admin/**").authenticated()
				.antMatchers("/rest/admin/**").authenticated()
				.antMatchers("/**").permitAll()
			.and()
				.formLogin()
					.loginPage("/admin/login")
					.defaultSuccessUrl("/admin/home")
					.loginProcessingUrl("/admin/auth/processLogin")
					.permitAll()
			.and()
				.logout()
				.logoutUrl("/admin/logout")
				.permitAll()
			.and()
				.addFilterBefore(
						new CookieAuthenticationFilter(cookieUserDetailsService , HttpSupportUtils.createOrRequestMathcers(SECURITY_URL_PATTERNS))
						, AnonymousAuthenticationFilter.class)
				
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		auth
			.userDetailsService(adminUserDetailsService)
			.passwordEncoder(bCryptPasswordEncoder)
			;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}
											