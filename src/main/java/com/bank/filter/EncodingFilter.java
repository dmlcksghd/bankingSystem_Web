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
import javax.servlet.http.HttpServletRequest;


@WebFilter("/*")
public class EncodingFilter extends HttpFilter implements Filter {
 
	private static final long serialVersionUID = 1L;
	private String encoding = "UTF-8";
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 요청과 응답의 인코딩 설정
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        
        // 응답 Conten-Type을 요청 URL에 따라 조건부로 설정
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri.endsWith(".css")) {
            response.setContentType("text/css; charset=" + encoding);
        } else if (uri.endsWith(".js")) {
            response.setContentType("application/javascript; charset=" + encoding);
        } else if (uri.endsWith(".json")) {
            response.setContentType("application/json; charset=" + encoding);
        } else {
            response.setContentType("text/html; charset=" + encoding); // 기본 설정
        }

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
