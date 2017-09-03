package kr.cnkisoft.framework.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class HttpSupportUtils {
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookieByName(request, cookieName);
		
		return cookie == null ? null : cookie.getValue();
	}
	
	public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
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
	
	public static OrRequestMatcher createOrRequestMathcers(String[] urlPatternArray) {
		List<RequestMatcher> matcherList = new ArrayList<>();
		
		for (String urlPattern : urlPatternArray) {
			matcherList.add(new AntPathRequestMatcher(urlPattern));
		}
		
		OrRequestMatcher requestMatcher = new OrRequestMatcher(matcherList);
		
		return requestMatcher;
	}
}
