package kr.cnkisoft.framework.filter;

import kr.cnkisoft.framework.security.LoginUserDetails;
import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

		Cookie authCookie = getCookieByName(request, COOKIE_NAME);

		String requestUri = request.getRequestURI().toString().trim();

		if (requestUri.equals("/")) {
			String hpNum = request.getParameter("hpnum");

			if (StringUtils.isEmpty(hpNum)) {
				hpNum = "01000000000";
			}
			authCookie = new Cookie("KidsStory", hpNum);
			authCookie.setMaxAge(-1);
			authCookie.setPath("/");

			response.addCookie(authCookie);
		}

		log.info(requestUri);

		if (authCookie != null) {

			if (StringUtils.isEmpty(authCookie.getValue())) {
				throw new BadCredentialsException("invalid Auth");
			}

			UserDetails userDetails = userDetailsService.loadUserByUsername(authCookie.getValue());

			log.info(((LoginUserDetails)userDetails).getLoginUser().toString());

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

	private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}

		return null;
	}
}
