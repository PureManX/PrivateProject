/*
  * Copyright (c) 2017 SK TECHX.
  * All right reserved.
  *
  * This software is the confidential and proprietary information of SK TECHX.
  * You shall not disclose such Confidential Information and
  * shall use it only in accordance with the terms of the license agreement
  * you entered into with SK TECHX.
 */
package kr.cnkisoft.framework.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

public class HttpRequestWrapper extends HttpServletRequestWrapper {
	private final Charset encoding;
	private byte[] body;

	public HttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		
		String characterEncoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(characterEncoding)) {
            characterEncoding = StandardCharsets.UTF_8.name();
        }
        this.encoding = Charset.forName(characterEncoding);
		
		InputStream is = super.getInputStream();
		body = IOUtils.toByteArray(is);
	}

    @Override
    public ServletInputStream getInputStream () throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        ServletInputStream inputStream = new ServletInputStream() {
        	
            public int read () 
                throws IOException {
                return byteArrayInputStream.read();
            }

			@Override
			public boolean isFinished() { return false; }

			@Override
			public boolean isReady() { return false; }

			@Override
			public void setReadListener(ReadListener readListener) {}
        };
        return inputStream;
    }
    
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }

    @Override
    public ServletRequest getRequest() {
        return super.getRequest();
    } 
}
