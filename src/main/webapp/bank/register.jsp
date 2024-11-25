<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
    <title>회원가입</title>
    
    <!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${path}/css/register.css">

</head>
<body>
	<div class="container">
    	<h1>회원가입</h1>
    	<form action="${path}/bank/auth.do" method="post">
			<input type="hidden" name="action" value="register"> <!-- 회원가입 액션 -->
			
			<!-- 아이디 입력 -->
            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요." required>
            </div>
            
			<!-- 이름 입력 -->
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요." required>
            </div>

            <!-- 주민등록번호 입력 -->
            <div class="form-group">
                <label for="ssn">주민등록번호</label>
                <input type="text" id="ssn" name="ssn" placeholder="숫자만 입력하세요." required>
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
            
			<!-- 회원가입 버튼 -->
            <button type="submit">회원가입</button>

            <!-- 로그인 링크 -->
            <p>이미 계정이 있으신가요? <a href="${path}/bank/login.jsp">로그인</a></p>
        </form>
    </div>
</body>
</html>
