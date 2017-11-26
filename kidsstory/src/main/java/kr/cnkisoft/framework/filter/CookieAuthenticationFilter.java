package kr.cnkisoft.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import kr.cnkisoft.framework.security.LoginUserDetails;
import kr.cnkisoft.framework.utils.HttpSupportUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by PureMaN on 2017-06-01.
 */
@Slf4j
public class CookieAuthenticationFilter extends GenericFilterBean {

	private static final String COOKIE_NAME = "KidsStory";

	UserDetailsService userDetailsService;
	RequestMatcher requestMatcher;

	public CookieAuthenticationFilter(UserDetailsService userDetailsService, RequestMatcher requestMatcher) {
		this.userDetailsService = userDetailsService;
		this.requestMatcher = requestMatcher;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (requireAuthentication(request)) {
			Cookie authCookie = getAuthentication(request, response);
			
			if (authCookie != null) {
				
				if (StringUtils.isEmpty(authCookie.getValue())) {
					throw new BadCredentialsException("invalid Auth");
				}
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(authCookie.getValue());
				
				log.info(((LoginUserDetails)userDetails).getLoginUser().getUser().toString());
				
//				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(token);
				
				filterChain.doFilter(request, response);
				
				SecurityContextHolder.clearContext();
			} else {
//				filterChain.doFilter(request, response);
				response.sendRedirect("/");
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
	
	private Cookie getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String requestUri = request.getRequestURI().toString().trim();
		
		if (requestUri.equals("/")) {
			return setAuthenticationFromParameter(request, response);
		} else {
			return getAuthenticationFromCookies(request, response);
		}
		
	}
	
	private Cookie setAuthenticationFromParameter(HttpServletRequest request, HttpServletResponse response) {
		String hpNum = request.getParameter("hpnum");
		Cookie storedCookie =  HttpSupportUtils.getCookieByName(request, COOKIE_NAME);
		
		if (StringUtils.isEmpty(hpNum) && storedCookie == null) {
			hpNum = "01000000000";
		} else if (StringUtils.isEmpty(hpNum) && storedCookie != null) {
			hpNum = storedCookie.getValue();
		}
		
		Cookie authCookie = new Cookie("KidsStory", hpNum);
		authCookie.setMaxAge(-1);
		authCookie.setPath("/");

		response.addCookie(authCookie);
		
		return authCookie;
	}
	
	private Cookie getAuthenticationFromCookies(HttpServletRequest request, HttpServletResponse response) {
		return HttpSupportUtils.getCookieByName(request, COOKIE_NAME);
	}

	private boolean requireAuthentication(HttpServletRequest request) {
		return !this.requestMatcher.matches(request);
	}
}
