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
        
        if (message) {
            const alertBox = document.querySelector("#alert-box");
            const alertMessage = document.querySelector("#alert-message");
            const alertClose = document.querySelector("#alert-close");

            // 알림 메시지 표시
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
    
    <style>
/* 공통 스타일 */
body {
    font-family: 'Arial', sans-serif;
    color: #333;
    margin: 0;
    padding: 0;
}

.container {
    margin: 20px auto;
    padding: 20px;
    background-color: #FFFFFF;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-width: 1000px;
}

/* 메뉴 스타일 */


/* 테이블 스타일 */
.account-table,
.card-container table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

.account-table th,
.account-table td,
.card-container th,
.card-container td {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: center;
}

.account-table thead,
.card-container thead {
    background-color: #007AFF;
    color: white;
}

.account-table tbody tr:nth-child(even),
.card-container tr:nth-child(even) {
    background-color: #F9FAFB;
}

.account-table tbody tr:hover,
.card-container tr:hover {
    background-color: #f1f1f1;
}

.card-container tr:hover th {
    background-color: #007AFF; /* hover 시 th 유지 */
    color: white;
}

/* 상태 스위치 */
.status-switch {
    display: flex;
    align-items: center;
    flex-direction: column;
}

.status-text {
    font-size: 14px;
    margin-bottom: 5px;
    color: #555;
}

.switch {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 25px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    transition: 0.4s;
    border-radius: 34px;
}

.slider:before {
    position: absolute;
    content: "";
    height: 17px;
    width: 17px;
    left: 4px;
    bottom: 4px;
    background-color: white;
    transition: 0.4s;
    border-radius: 50%;
}

input:checked + .slider {
    background-color: #007AFF;
}

input:checked + .slider:before {
    transform: translateX(25px);
}

/* 송금 폼 */
.transfer-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
}

.transfer-form input[type="text"],
.transfer-form input[type="number"] {
    width: 80%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    background-color: #F9FAFB;
}

.transfer-form input[type="text"]:focus,
.transfer-form input[type="number"]:focus {
    border-color: #007AFF;
    outline: none;
    background-color: #FFFFFF;
    box-shadow: 0 0 5px rgba(0, 122, 255, 0.3);
}

.transfer-form button {
    width: 80%;
    padding: 12px 0;
    background-color: #007AFF;
    color: #FFFFFF;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    transition: all 0.3s ease;
}

.transfer-form button:hover {
    background-color: #005BBB;
}

/* 카드 보기 버튼 스타일 */
.link-card-btn {
    display: inline-block;
    padding: 10px 20px;
    background-color: #007AFF;
    color: #FFFFFF;
    border: none;
    border-radius: 5px;
    font-size: 14px;
    font-weight: bold;
    cursor: pointer;
    transition: all 0.3s ease;
    text-align: center;
}

.link-card-btn:hover {
    background-color: #005BBB;
}

/* 카드 컨테이너 */
.card-container {
    padding: 10px;
    background-color: #F9FAFB;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-top: 10px;
    text-align: center; /* 카드 목록 중앙 정렬 */
}

.card-container table {
    margin: 0 auto; /* 카드 테이블 중앙 배치 */
    border-collapse: collapse;
    width: auto;
    max-width: 80%;
}

/* 알림 창 스타일 */
.alert-box {
    position: fixed;
    top: 20%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #FFFFFF;
    color: #007AFF;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    text-align: center;
    font-size: 16px;
}

.alert-box button {
    margin-top: 10px;
    padding: 5px 10px;
    background-color: white;
    color: #007AFF;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
}

.alert-box button:hover {
    background-color: #f1f1f1;
}
    </style>
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
						                    <span class="slider">CLOSED</span>
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