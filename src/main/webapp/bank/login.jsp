<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
    <title>로그인</title>
    
    <!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">

</head>
<body>
	<div class="container">
    	<h1>로그인</h1>
	    <form action="${path}/bank/auth.do" method="post">
	        <input type="hidden" name="action" value="login"> <!-- 로그인 페이지 액션 -->
	        
	        <!-- 아이디 입력 -->
	        <div class="form-group">
		        <label for="userId">아이디</label>
		        <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요." required>
	        </div>
	       
	        <!-- 비밀번호 입력 -->
	        <div class="form-group">
		        <label for="password">비밀번호</label>
		        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요." required>
	        </div>
	        
	        <!-- 에러 메시지 -->
	        <c:if test="${not empty errorMessage}">
	        	<div class="error-message">${errorMessage}</div>
	        </c:if>
	        
	        <!-- 로그인 버튼 -->
	        <button type="submit">로그인</button>
	        
	        <!-- 회원가입 링크 -->
	    	<p>계정이 없으신가요? <a href="register.jsp">회원가입</a></p>
	    </form>
    </div>
</body>
</html>