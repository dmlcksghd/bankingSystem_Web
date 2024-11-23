<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

	<link rel="stylesheet" type="text/css" href="${path}/css/accounts.css">
	
	<title>계좌 목록</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function() {
    // 카드 추가 버튼 클릭 이벤트
    $(".add-card-btn").click(function() {
        var accountNo = $(this).data("account-no");
        
        // 카드 추가 폼 로드
        $.ajax({
            url: "${pageContext.request.contextPath}/bank/cards?action=loadAddCardForm",
            type: "GET",
            data: { accountNo: accountNo },
            success: function(response) {
                // 응답 받은 폼 동적으로 표시
                $("#card-form-container").html(response);
            },
            error: function() {
                alert("카드 추가 폼을 로드하는 데 실패했습니다.");
            }
        });
    });
    
    // 카드 추가 폼 제출 이벤트
    $(document).ready(function() {
        $(".add-card-btn").click(function() {
            var accountNo = $(this).data("account-no");

            $.ajax({
                url: "${pageContext.request.contextPath}/bank/cards",
                type: "GET",
                data: { action: "loadAddCardForm", accountNo: accountNo },
                success: function(response) {
                    $("#card-form-container").html(response);
                },
                error: function() {
                    alert("카드 추가 폼을 로드하는 데 실패했습니다.");
                }
            });
        });
    });
});
</script>


</head>
<body>
	<%@ include file="header.jsp"%>
	<table border="1">
    <thead>
        <tr>
            <th>계좌 번호</th>
            <th>잔액</th>
            <th>상태</th>
            <th>상태 변경</th>
            <th>송금</th>
            <th>연결된 카드</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="account" items="${accounts}">
        <tr>
            <td>${account.accountNo}</td>
            <td>${account.balance}</td>
            <td>${account.status}</td>
            <td>
    			<c:choose>
        		<c:when test="${account.status == 'CLOSED'}">
            		<div>
	                	<input class="form-check-input" type="checkbox" role="switch" id="statusSwitch${account.accountNo}" disabled>
	                	<label class="form-check-label" for="statusSwitch${account.accountNo}">폐쇄 상태</label>
            		</div>
        			</c:when>
        			<c:otherwise>
		            <form action="${pageContext.request.contextPath}/bank/accounts" method="POST">
		                <input type="hidden" name="action" value="updateStatus">
		                <input type="hidden" name="accountNo" value="${account.accountNo}">
		                
		                <!-- 체크박스 -->
		                <input class="form-check-input" type="checkbox" role="switch" name="status" value="ACTIVE"
		                       <c:if test="${account.status == 'ACTIVE'}">checked</c:if>
		                       onchange="this.checked ? this.value='ACTIVE' : this.value= 'INACTIVE'; this.form.submit();">
		                <label class="form-check-label" for="statusSwitch${account.accountNo}">
		                    <c:if test="${account.status == 'ACTIVE'}">활성화</c:if>
		                    <c:if test="${account.status == 'INACTIVE'}">비활성화</c:if>
		                </label>
		            </form>
        		</c:otherwise>
    			</c:choose>
			</td>
			<td>
			    <c:choose>
			        <c:when test="${account.status == 'ACTIVE'}">
			            <form action="${pageContext.request.contextPath}/bank/accounts" method="POST">
			                <input type="hidden" name="action" value="transfer">
			                <input type="hidden" name="fromAccountNo" value="${account.accountNo}">
			                <input type="hidden" name="type" value="TRANSFER">
			                <input type="text" name="toAccountNo" placeholder="받는 계좌 번호">
			                <input type="number" name="amount" placeholder="금액">
			                <button type="submit">송금</button>
			            </form>
			        </c:when>
			        <c:otherwise>
			            <button type="button" disabled>송금 불가</button>
			        </c:otherwise>
			    </c:choose>
			</td>
			<td>
			    <form action="${pageContext.request.contextPath}/bank/cards" method="GET">
			        <input type="hidden" name="action" value="viewCards">
			        <input type="hidden" name="accountNo" value="${account.accountNo}">
			        <button type="submit">연결된 카드 보기</button>
			    </form>
			</td>
        </tr>
    </c:forEach>
</tbody>
</table>
</body>
</html>