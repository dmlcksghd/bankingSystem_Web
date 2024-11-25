<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>거래 내역</title>
    <%@ include file="menu.jsp" %>
	<%@ include file="header.jsp" %>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/transactions.css">
	
</head>
<body>
    <div class="container">
        <h1>거래 내역</h1>

        <!-- 계좌 선택 -->
        <label for="account-select">계좌 선택:</label>
        <select id="account-select">
            <option value="">계좌를 선택하세요</option>
            <c:forEach var="account" items="${accounts}">
                <option value="${account.accountNo}">
                    ${account.accountNo} (${account.balance}원)
                </option>
            </c:forEach>
        </select>

        <!-- 거래 내역 테이블 -->
        <table class="transaction-table" id="transaction-table">
            <thead>
                <tr>
                    <th>거래 ID</th>
                    <th>계좌 번호</th>
                    <th>금액</th>
                    <th>유형</th>
                    <th>날짜</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td colspan="5">계좌를 선택하세요.</td>
                </tr>
            </tbody>
        </table>
    </div>

    <script>
    $(document).ready(function () {
        $("#account-select").change(function () {
            const accountNo = $(this).val();
            const tbody = $("#transaction-table tbody");

            // 테이블 초기화
            tbody.empty();

            if (accountNo) {
                $.ajax({
                    url: "/testWeb/bank/transactions",
                    type: "GET",
                    data: { accountNo: accountNo },
                    success: function (htmlResponse) {
                        console.log("응답 HTML:", htmlResponse);
                        tbody.html(htmlResponse); // 서버에서 생성된 HTML 삽입
                    },
                    error: function (xhr, status, error) {
                        console.error("AJAX 요청 실패:", error);
                        tbody.append("<tr><td colspan='5'>거래 내역을 불러오는 중 오류가 발생했습니다.</td></tr>");
                    }
                });
            } else {
                tbody.append("<tr><td colspan='5'>계좌를 선택하세요.</td></tr>");
            }
        });
    });
    </script>
</body>
</html>
