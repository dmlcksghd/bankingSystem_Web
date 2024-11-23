<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
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
    </script>
    
    <style>
		body {
		    font-family: 'Arial', sans-serif;
		    background-color: #F7F9FC;
		    color: #333; /* 텍스트 기본 색상 */
		    margin: 0;
		    padding: 20px;
		    display: flex;
		    justify-content: center;
		    align-items: flex-start;
		    min-height: 100vh;
		}
		
		.container {
		    width: 80%;
		    background-color: #FFFFFF; /* 카드형 배경 */
		    border-radius: 10px;
		    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
		    padding: 20px;
		}
		
		h1 {
		    font-size: 24px;
		    color: #007AFF; /* 스타일 주요 색상 */
		    margin-bottom: 20px;
		    text-align: center;
		}
		
		.account-table {
		    width: 100%;
		    border-collapse: collapse;
		    margin-bottom: 20px;
		}
		
		.account-table thead {
		    background-color: #007AFF;
		    color: #FFFFFF;
		}
		
		.account-table th,
		.account-table td {
		    padding: 15px;
		    text-align: center;
		    border: 1px solid #ddd;
		}
		
		.account-table tr:nth-child(even) {
		    background-color: #F9FAFB;
		}
		
		.account-table tr:hover {
		    background-color: #f1f1f1;
		}
		
		thead tr:hover th {
		    background-color: #007AFF; /* 기존 헤더 배경색 유지 */
		    cursor: default; /* 마우스 커서 변경 방지 */
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
		
		/* 스위치 스타일 */
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
		
		.switch.disabled .slider {
		    background-color: #ddd; /* 비활성화된 상태 색상 */
		    cursor: not-allowed;
		}
		
		.switch.disabled .slider:before {
		    background-color: #bbb; /* 비활성화된 상태에서 내부 색상 */
		}
		
		/* 송금 버튼 */
		input[type="text"],
		input[type="number"] {
		    width: 90%; /* 크기를 적절히 축소 */
		    padding: 8px; /* 여백 축소 */
		    border: 1px solid #ddd;
		    border-radius: 5px;
		    font-size: 13px; /* 폰트 크기 축소 */
		    background-color: #F9FAFB;
		    transition: all 0.3s ease;
		    margin-bottom: 10px; /* 입력 필드 간 간격 추가 */
		}
		
		input[type="text"]:focus,
		input[type="number"]:focus {
		    border-color: #007AFF;
		    outline: none;
		    background-color: #FFFFFF;
		    box-shadow: 0 0 3px rgba(0, 122, 255, 0.3); /* 포커스 효과 최소화 */
		}
		
		button {
		    width: auto; /* 버튼 크기를 내용에 맞게 자동 조정 */
		    padding: 8px 20px; /* 여백 축소 */
		    background-color: #007AFF;
		    color: #FFFFFF;
		    border: none;
		    border-radius: 5px;
		    font-size: 14px; /* 폰트 크기 축소 */
		    font-weight: bold;
		    cursor: pointer;
		    transition: all 0.3s ease;
		}
		
		button:hover {
		    background-color: #005BBB;
		}
		
		/* 카드 컨테이너 */
		.card-container {
			display: flex;
		    flex-direction: column; /* 세로 정렬 */
		    align-items: center;
		    justify-content: center;
		    margin: 20px 0;
		    padding: 10px;
		    background-color: #f9f9f9;
		    border: 1px solid #ddd;
		    border-radius: 10px;
		    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
		    width: 80%;             /* 컨테이너 너비 설정 */
		    margin-left: auto;      /* 중앙 정렬을 위해 추가 */
		    margin-right: auto;     /* 중앙 정렬을 위해 추가 */
		}
    </style>
</head>
<body>
    <%@ include file="header.jsp"%>
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
                            <form action="${pageContext.request.contextPath}/bank/accounts" method="POST">
                                <input type="hidden" name="action" value="transfer">
                                <input type="hidden" name="fromAccountNo" value="${account.accountNo}">
                                <input type="hidden" name="type" value="TRANSFER">
                                <input type="text" name="toAccountNo" placeholder="받는 계좌 번호">
                                <input type="number" name="amount" placeholder="금액">
                                <button type="submit">송금</button>
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
</body>
</html>
