<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
</style>
</head>
<body>
<nav class="menu-container">
    <a href="${path}/bank/main.jsp" class="${page == 'main' ? 'active' : ''}">메인</a>
	<a href="${path}/bank/accounts">계좌 관리</a>
    <a href="${path}/bank/transactions.jsp">거래 내역</a>
    <a href="${path}/bank/cards.jsp">카드 관리</a>
    <a href="${path}/bank/accountManagement.jsp">계정 정보 변경</a>
    <a href="${path}/bank/logout.jsp">로그아웃</a>
</nav>
</body>
</html>