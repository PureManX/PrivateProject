package kr.cnkisoft.framework.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
