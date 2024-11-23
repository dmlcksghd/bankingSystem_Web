<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 정보관리</title>
</head>
<body> 
	<%@ include file="header.jsp"%>
	<h1>계정 정보 관리</h1>
	<form action="${pageContext.request.contextPath}/auth" method="POST">
        <input type="hidden" name="action" value="updateUserId">
        <label for="newUserId">새 아이디:</label>
        <input type="text" id="newUserId" name="newUserId">
        <button type="submit">아이디 변경</button>
    </form>

    <form action="${pageContext.request.contextPath}/auth" method="POST">
        <input type="hidden" name="action" value="updatePassword">
        <label for="newPassword">새 비밀번호:</label>
        <input type="password" id="newPassword" name="newPassword">
        <button type="submit">비밀번호 변경</button>
    </form>
</body>
</html>