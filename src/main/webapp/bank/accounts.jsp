<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="menu.jsp" %>
	<%@ include file="header.jsp" %>
	
    <title>계좌 목록</title>
    
    <!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${path}/css/accounts.css">
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
        $(document).ready(function () {
            // 연결된 카드 보기/숨기기
            $(".link-card-btn").click(function () {
                var accountNo = $(this).data("account-no");
                var container = $("#card-container-" + accountNo);

                if (container.is(":visible")) {
                    container.hide(); // 카드 목록 숨기기
                } else {
                    // 카드 목록 가져오기
                    $.ajax({
                        url: "${path}/bank/cards",
                        type: "GET",
                        data: { action: "viewCards", accountNo: accountNo },
                        success: function (response) {
                            container.html(response).show();
                        },
                        error: function () {
                            alert("카드 데이터를 가져오는 데 실패했습니다.");
                        },
                    });
                }
            });

            // 스위치 상태 변경
            $(".status-switch input").change(function () {
                const isActive = $(this).prop("checked");
                const accountNo = $(this).closest("form").find("input[name='accountNo']").val();
                const statusText = $(this).closest(".status-switch").find(".status-text");

                $.ajax({
                    url: "${path}/bank/accounts",
                    type: "POST",
                    data: {
                        action: "updateStatus",
                        accountNo: accountNo,
                        status: isActive ? "ACTIVE" : "INACTIVE",
                    },
                    success: function () {
                        // 상태 텍스트 업데이트
                        statusText.text(isActive ? "ACTIVE" : "INACTIVE");
                        alert("상태가 성공적으로 변경되었습니다.");
                    },
                    error: function () {
                        alert("상태 변경에 실패했습니다.");
                    },
                });
            });

        });
        
        // 알림창
        document.addEventListener("DOMContentLoaded", function () {
        	// 서버에서 전달받은 메시지 가져오기
        	const message = "<c:out value='${sessionScope.transferMessage}' />";
            const alertBox = document.querySelector("#alert-box");
            const alertMessage = document.querySelector("#alert-message");
            const alertClose = document.querySelector("#alert-close");
        
            if (message && message.trim() !== "") {
            alertMessage.innerText = message;
            alertBox.style.display = "block";

            // 닫기 버튼 클릭 시 알림 창 닫기
            alertClose.addEventListener("click", function () {
                alertBox.style.display = "none";
            });

            // 2초 후에 자동으로 닫기
            setTimeout(function () {
                alertBox.style.display = "none";
            }, 2000);
        }
    });
    </script>
    <!-- 세션 메시지 초기화 -->
	<%
	    // 세션 메시지를 제거하여 반복 표시 방지
	    session.removeAttribute("transferMessage");
	%>
</head>
<body>  
    <div class="container">
        <h1>계좌 목록</h1>
        <table class="account-table">
            <thead>
                <tr>
                    <th>계좌 번호</th>
                    <th>잔액</th>
                    <th>상태</th>
                    <th>송금</th>
                    <th>연결된 카드</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="account" items="${accounts}">
                    <tr>
                        <td>${account.accountNo}</td>
                        <td>${account.balance}</td>
						<td>
						    <div class="status-switch">
						        <!-- 상태 텍스트 추가 -->
						        <div class="status-text">
						            <c:choose>
						                <c:when test="${account.status == 'CLOSED'}">CLOSED</c:when>
						                <c:when test="${account.status == 'ACTIVE'}">ACTIVE</c:when>
						                <c:otherwise>INACTIVE</c:otherwise>
						            </c:choose>
						        </div>
						        <c:choose>
						            <c:when test="${account.status == 'CLOSED'}">
						                <label class="switch disabled">
						                    <input type="checkbox" disabled>
						                    <span class="slider"></span>
						                </label>
						            </c:when>
						            <c:otherwise>
						                <form action="${pageContext.request.contextPath}/bank/accounts" method="POST">
						                    <input type="hidden" name="action" value="updateStatus">
						                    <input type="hidden" name="accountNo" value="${account.accountNo}">
						                    <label class="switch">
						                        <input type="checkbox" name="status" value="ACTIVE" 
						                            <c:if test="${account.status == 'ACTIVE'}">checked</c:if>
						                            onchange="this.checked ? this.value='ACTIVE' : this.value='INACTIVE'; this.form.submit();">
						                        <span class="slider"></span>
						                    </label>
						                </form>
						            </c:otherwise>
						        </c:choose>
						    </div>
						</td>
                        <td>
                            <form class="transfer-form" action="${pageContext.request.contextPath}/bank/accounts" method="POST">
                                <input type="hidden" name="action" value="transfer">
                                <input type="hidden" name="fromAccountNo" value="${account.accountNo}">
                                <input type="hidden" name="type" value="TRANSFER">
                                
                                <input type="text" name="toAccountNo" placeholder="받는 계좌 번호"
                                <c:if test="${account.status != 'ACTIVE'}">disabled</c:if>>
                                
                                <input type="number" name="amount" placeholder="금액"
                                <c:if test="${account.status != 'ACTIVE'}">disabled</c:if>>
                                
                                <button type="submit"
                                <c:if test="${account.status != 'ACTIVE'}">disabled</c:if>>송금</button>
                            </form>
                        </td>
                        <td>
                            <button class="link-card-btn" data-account-no="${account.accountNo}">카드 보기</button>
                        </td>
                    </tr>
                    <!-- 카드 데이터를 표시할 컨테이너 -->
                    <tr>
                        <td colspan="5">
                            <div id="card-container-${account.accountNo}" class="card-container" style="display: none;">
                                <!-- Ajax 요청으로 동적으로 데이터가 추가됩니다 -->
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <!-- 알림 창 -->
    <div id="alert-box" class="alert-box" style="display: none;">
    <span id="alert-message"></span>
    <button id="alert-close">닫기</button>
	</div>
</body>
</html>