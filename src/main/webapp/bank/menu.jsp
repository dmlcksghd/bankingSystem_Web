<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

	<!-- 외부 스타일시트 연결 -->
	<link rel="stylesheet" type="text/css" href="${path}/css/menu.css">

</head>
<body>
<!-- 네비게이션 바 -->
<div class="navbar">
	<a href="${path}/bank/accounts?view=main">홈</a>
    <a href="${path}/bank/accounts?view=accounts">계좌 관리</a>
    <a href="${path}/bank/transactions">거래 내역</a>
    <%-- <a href="${path}/bank/cards.jsp">카드 관리</a> --%>
    <a href="${path}/bank/accountManagement
    ">계정 정보 변경</a>
    <a href="${path}/bank/auth.do">로그아웃</a>
</div>

</body>
</html>