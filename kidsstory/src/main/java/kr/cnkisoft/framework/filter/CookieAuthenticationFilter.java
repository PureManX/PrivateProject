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

	public CookieAuthenticationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Cookie authCookie = HttpSupportUtils.getCookieByName(request, COOKIE_NAME);

		String requestUri = request.getRequestURI().toString().trim();

		addAuthenticationCookies(request, response);

		if (authCookie != null) {

			if (StringUtils.isEmpty(authCookie.getValue())) {
				throw new BadCredentialsException("invalid Auth");
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(authCookie.getValue());

			log.info(((LoginUserDetails)userDetails).getLoginUser().getUser().toString());

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null);

			SecurityContextHolder.getContext().setAuthentication(token);

			filterChain.doFilter(request, response);

			SecurityContextHolder.clearContext();
		} else {
			if (requestUri.equals("/") || requestUri.startsWith("/auth/")) {
				filterChain.doFilter(request, response);
			} else {
				response.sendRedirect("/auth/request");
			}
		}
	}
	
	private void addAuthenticationCookies(HttpServletRequest request, HttpServletResponse response) {
		String requestUri = request.getRequestURI().toString().trim();
		
		if (requestUri.equals("/")) {
			String hpNum = request.getParameter("hpnum");

			if (StringUtils.isEmpty(hpNum)) {
				String cookileHpNum = HttpSupportUtils.getCookieValue(request, COOKIE_NAME);
				if (cookileHpNum != null) {
					hpNum = cookileHpNum;
				} else {
					hpNum = "01000000000";
				}
			}
			
			Cookie authCookie = new Cookie("KidsStory", hpNum);
			authCookie.setMaxAge(-1);
			authCookie.setPath("/");

			response.addCookie(authCookie);
		}
	}

}
