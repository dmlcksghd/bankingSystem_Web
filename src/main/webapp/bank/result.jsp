<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp" %>
	<meta charset="UTF-8">
	
	<title>회원가입 결과</title>
	
	<!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${path}/css/result.css">
    
	<script>
		window.onload = function () {
	        logoutTimer = setTimeout(function () {
	            window.location.href = "${path}/logout";
	        }, 3000);
	    };
	    
	    function cancelLogoutAndRedirect() {
	    	clearTimeout(logoutTimer); // 로그아웃 타이머 중지
            window.location.href = "${path}/bank/accountManagement"; // 다른 페이지로 이동
	    }
    </script>
</head>
    </div>
<body>
	<div class="container">
		<p class="message"><span>로그아웃</span>이 완료되었습니다.</p>
		<p class="redirect-info">돌아가기를 누르지 않으면 3초 후 로그인 페이지로 이동합니다.</p>
		<div class="button-container">
            <button onclick="window.location.href='<%= request.getContextPath() %>/bank/accountManagement'">돌아가기</button>
        </div>
	</div>
</body>
</html>