<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
    <title>메인 페이지</title>
    
    <link rel="stylesheet" type="text/css" href="${path}/css/main.css">

	<style>
		/* 기본 스타일 */
		body {
			font-family: 'Arial', sans-serif;
			background-color: #F7F9FC;
			color: #333;
			margin: 0;
			padding: 0;
		}
		
		/* 메인 컨테이너 */
		.maincontainer {
			margin: 20px auto;
			max-width: 1200px;
			background-color: #FFFFFF;
			border-radius: 10px;
			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
			padding: 20px;
		}
		
		/* 헤더 스타일 */
		.header {
			text-align: center;
			padding: 20px 0;
			background-color: #007AFF;
			color: #FFFFFF;
			border-radius: 10px 10px 0 0;
		}
		
		.header h1 {
			font-size: 28px;
			margin: 0;
			font-weight: bold;
		}
		
		.header p {
			margin: 5px 0;
			font-size: 16px;
		}
		
		/* 네비게이션 스타일 */
		.navbar {
			display: flex;
			justify-content: center;
			padding: 10px 0;
			background-color: #F7F9FC;
			border-bottom: 1px solid #ddd;
			margin-bottom: 20px;
		}
		
		.navbar a {
		    text-decoration: none;
		    margin: 0 15px;
		    font-size: 16px;
		    color: #007AFF;
		    transition: color 0.3s ease;
		}
		
		.navbar a:hover {
		    color: #005BBB;
		}
		
		/* 카드형 섹션 */
		.card {
		    background-color: #FFFFFF;
		    border: 1px solid #ddd;
		    border-radius: 10px;
		    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		    padding: 20px;
		    margin-bottom: 20px;
		}
		
		.card h2 {
		    font-size: 20px;
		    color: #007AFF;
		    margin-bottom: 10px;
		}
		
		.card p {
		    font-size: 14px;
		    line-height: 1.6;
		    color: #555;
		}
		
		.button {
			display: inline-block;
		    background-color: #007AFF;
		    color: #FFFFFF;
		    padding: 10px 20px;
		    border: none;
		    border-radius: 5px;
		    font-size: 16px;
		    font-weight: bold;
		    text-align: center;
		    text-decoration: none;
		    cursor: pointer;
		    transition: background-color 0.3s ease;
		}
		
		.button:hover {
		    background-color: #005BBB;
		}
		
		/* 테이블 스타일 */
		.table {
		    width: 100%;
		    border-collapse: collapse;
		    margin-bottom: 20px;
		}
		
		.table th, .table td {
		    text-align: left;
		    padding: 10px;
		    border: 1px solid #ddd;
		}
		
		.table th {
		    background-color: #F1F1F1;
		    color: #333;
		    font-weight: bold;
		}
		
		.table td {
		    color: #555;
		}
		
		.table tr:nth-child(even) {
		    background-color: #F9FAFB;
		}
		
		/* 하단 정보 */
		.footer {
		    text-align: center;
		    padding: 10px 0;
		    background-color: #F1F1F1;
		    color: #777;
		    border-radius: 0 0 10px 10px;
		    margin-top: 20px;
		    font-size: 14px;
		}				
	</style>
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
        <a href="${path}/bank/transactions.jsp">거래 내역</a>
        <%-- <a href="${path}/bank/cards.jsp">카드 관리</a> --%>
        <a href="${path}/bank/accountManagement.jsp">계정 정보 변경</a>
        <a href="${path}/bank/logout.jsp">로그아웃</a>
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
            <a href="${path}/bank/transactions.jsp" class="button">전체 거래 내역 보기</a>
        </div>
    </div>
    
    <!-- 푸터 -->
    <div class="footer">
        <p>인터넷 뱅킹 서비스 &copy; 2024. All rights reserved.</p>
    </div>        
</body>
</html>


