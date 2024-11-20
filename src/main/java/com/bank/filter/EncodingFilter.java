package com.bank.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;


@WebFilter("/*")
public class EncodingFilter extends HttpFilter implements Filter {
 
	private String encoding = "UTF-8";
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 요청과 응답의 인코딩 설정
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=" + encoding);

        // 다음 필터나 서블릿 호출
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		String configEncoding = fConfig.getInitParameter("encoding");
        if (configEncoding != null) {
            encoding = configEncoding; // web.xml에서 설정된 인코딩 사용
        }
	}

}
