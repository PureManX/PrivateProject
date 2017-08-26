package kr.cnkisoft.framework.config;

import kr.cnkisoft.framework.filter.CookieAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Created by PureMaN on 2017-05-10.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.antMatcher("/**").anonymous()
				.and()
				.addFilterBefore(new CookieAuthenticationFilter(userDetailsService), AnonymousAuthenticationFilter.class)
		;
	}
}
											