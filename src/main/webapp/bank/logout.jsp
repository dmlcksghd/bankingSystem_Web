<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // 세션 무효화
    session.invalidate();
    // 로그아웃 후 register.jsp로 이동
    response.sendRedirect("login.jsp");
%>
    