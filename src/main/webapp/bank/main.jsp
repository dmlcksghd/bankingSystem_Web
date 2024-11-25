<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
    <title>메인 페이지</title>
    
    <!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${path}/css/main.css">

</head>
<body>
	<!-- 헤더 -->
	<div class="header">
        <h1>인터넷 뱅킹</h1>
        <p>안녕하세요, ${user.userId}님! 반갑습니다.</p>
    </div>

	<!-- 네비게이션 바 -->
	<div class="navbar">
        <a href="${path}/bank/accounts?view=accounts">계좌 관리</a>
        <a href="${path}/bank/transactions">거래 내역</a>
        <%-- <a href="${path}/bank/cards.jsp">카드 관리</a> --%>
        <a href="${path}/bank/accountManagement">계정 정보 변경</a>
        <a href="${path}/bank/auth.do">로그아웃</a>
    </div>
    
    <!-- 메인 컨텐츠 -->
	<div class="main-container">
        <!-- 카드형 섹션 -->
        <div class="card">
            <h2>내 계좌 현황</h2>
            <p><strong>총 계좌 수:</strong> ${totalAccounts}개</p>
            <p><strong>총 자산:</strong> ${totalBalance} 원</p>
            <a href="${path}/bank/accounts?view=accounts" class="button">계좌 상세 보기</a>
        </div>

        <div class="card">
            <h2>최근 거래 내역</h2>
            <table class="table">
                <thead>
                    <tr>
		                <th>날짜</th>
		                <th>거래 유형</th>
		                <th>금액</th>
		                <th>계좌 번호</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="transaction" items="${recentTransactions}">
						<tr>
		                    <td><c:out value="${transaction.transactionDate}" /></td>
		                    <td><c:out value="${transaction.type}" /></td>
		                    <td><c:out value="${transaction.amount}" /></td>
		                    <td><c:out value="${transaction.accountNo}" /></td>
                        </tr>                	
                	</c:forEach>
                </tbody>
            </table>
            <a href="${path}/bank/transactions" class="button">전체 거래 내역 보기</a>
        </div>
    </div>
    
    <!-- 푸터 -->
    <div class="footer">
        <p>인터넷 뱅킹 서비스 &copy; 2024. All rights reserved.</p>
    </div>        
</body>
</html>


