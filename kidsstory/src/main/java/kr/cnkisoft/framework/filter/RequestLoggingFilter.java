/*
 *
 * Copyright (c) 2017 SK TECHX.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK TECHX.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK TECHX.
 *
 */
package kr.cnkisoft.framework.filter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class RequestLoggingFilter extends AbstractRequestLoggingFilter{
	
	RequestMatcher requestMatcher;
	
	public RequestLoggingFilter(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

	@Override
	protected boolean isIncludePayload() {
		return true;
	}

	@Override
	public boolean isIncludeHeaders() {
		return true;
	}
	
	@Override
	protected boolean isIncludeClientInfo() {
		return true;
	}
	@Override
	protected boolean isIncludeQueryString() {
		return true;
	}
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpRequestWrapper httpServletRequestWrapper = new HttpRequestWrapper(httpServletRequest);

		boolean requireLogging = requireLogging(request);
		if (requireLogging) {
			logger.info(createMessage(httpServletRequestWrapper, "Request Start == [", "]"));
		}
		
		filterChain.doFilter(httpServletRequestWrapper, response);
		
		if (requireLogging) {
			logger.info("Request End ==");
		}
	}
	
	@Override
	protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
		StringBuilder msg = new StringBuilder();
		msg.append(prefix);
		msg.append("uri=").append(request.getMethod())
		.append(" ").append(request.getRequestURI());

		if (isIncludeQueryString()) {
			String queryString = request.getQueryString();
			if (queryString != null) {
				msg.append('?').append(queryString);
			}
		}

		if (isIncludeClientInfo()) {
			String client = request.getRemoteAddr();
			if (StringUtils.hasLength(client)) {
				msg.append(";client=").append(client);
			}
			HttpSession session = request.getSession(false);
			if (session != null) {
				msg.append(";session=").append(session.getId());
			}
			String user = request.getRemoteUser();
			if (user != null) {
				msg.append(";user=").append(user);
			}
		}

		if (isIncludeHeaders()) {
			StringBuilder headers = new StringBuilder();
			
			
			Enumeration<String> headerNames = request.getHeaderNames();
			
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				
				String headerValue = request.getHeader(headerName);
				
				headers.append(headerName).append("=[").append(headerValue).append("], ");
				
			}
			
			msg.append(";headers=").append(headers);
		}


		if (isIncludePayload()) {
			String payload ="";
			StringWriter writer = new StringWriter();
			String encoding = request.getCharacterEncoding();
			try {
				 IOUtils.copy(request.getInputStream(), writer, encoding);
				 payload = writer.toString();
			} catch (Exception e) {
				payload = "[unknown]";
			}
			
			msg.append(";payload=").append(payload);
		}

		msg.append(suffix);
		return msg.toString();
	}

	private boolean requireLogging(HttpServletRequest request) {
		return !this.requestMatcher.matches(request);
	}
}
