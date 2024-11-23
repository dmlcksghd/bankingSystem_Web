<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
/* 네비게이션 스타일 */
.navbar {
    display: flex;
    justify-content: center;
    background-color: #FFFFFF; /* 배경 흰색 */
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
}

.navbar a {
    text-decoration: none;
    margin: 0 15px;
    font-size: 16px;
    color: #007AFF; /* 글씨 색 파란색 */
    transition: color 0.3s ease, text-decoration 0.3s ease;
    padding: 5px 10px;
}

.navbar a:hover {
    text-decoration: none; /* 밑줄 제거 */
    background-color: #F0F8FF; /* 호버 시 배경색 추가 */
    border-radius: 5px;
}

</style>
</head>
<body>
<!-- 네비게이션 바 -->
<div class="navbar">
	<a href="${path}/bank/main.jsp">홈</a>
    <a href="${path}/bank/accounts">계좌 관리</a>
    <a href="${path}/bank/transactions.jsp">거래 내역</a>
    <%-- <a href="${path}/bank/cards.jsp">카드 관리</a> --%>
    <a href="${path}/bank/accountManagement.jsp">계정 정보 변경</a>
    <a href="${path}/bank/logout.jsp">로그아웃</a>
</div>

</body>
</html>