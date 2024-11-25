<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <%@ include file="header.jsp" %>
    <%@ include file="menu.jsp" %> <!-- 메뉴 포함 -->
    
	<title>계정 정보 변경</title>

	<!-- CSS 외부 연결 -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/accountManagement.css">

</head>
<body> 
	<div class="container">
	<h1 class="main-title">계정 정보 변경</h1>
	
	<!-- 아이디 변경 폼 -->
	<form class="form-container" action="${pageContext.request.contextPath}/bank/auth.do" method="POST">
		<div class="form-group">
	        <label for="newUserId">새 아이디:</label>
	        <input type="hidden" name="action" value="updateUserId">
	        <input type="text" id="newUserId" name="newUserId" placeholder="새로운 아이디 입력">
	        <button type="submit">아이디 변경</button>
        </div>
    </form>

    <!-- 비밀번호 변경 폼 -->
    <form class="form-container" action="${pageContext.request.contextPath}/bank/auth.do" method="POST">
    	<div class="form-group">
	        <input type="hidden" name="action" value="updatePassword">
	        <label for="newPassword">새 비밀번호:</label>
	        <input type="password" id="newPassword" name="newPassword" placeholder="새로운 비밀번호 입력">
	        <button type="submit">비밀번호 변경</button>
        </div>
    </form>
	</div>
</body>
</html>
