<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

	<%@ include file="header.jsp"%>
	<%@ include file="menu.jsp"%>	<!-- 네비게이션 메뉴 포함 -->
	
	<title>거래 내역</title>
	
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
	<style>
		/* 기본 컨테이너 */
        .container {
            margin: 20px auto;
            max-width: 1000px;
            padding: 20px;
            background-color: #FFFFFF;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            text-align: center;
            color: #007AFF;
        }

        /* 거래 내역 테이블 */
        .transaction-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px auto;
            background-color: #FFFFFF;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .transaction-table th,
        .transaction-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }

        .transaction-table thead {
            background-color: #007AFF;
            color: #FFFFFF;
        }

        .transaction-table tbody tr:nth-child(even) {
            background-color: #F9FAFB;
        }

        .transaction-table tbody tr:hover {
            background-color: #f1f1f1;
        }

        /* 드롭다운 스타일 */
        .account-select-container {
            margin: 20px auto;
            text-align: center;
        }

        #account-select {
            width: 50%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #F9FAFB;
        }

        #account-select:focus {
            outline: none;
            border-color: #007AFF;
            background-color: #FFFFFF;
            box-shadow: 0 0 5px rgba(0, 122, 255, 0.3);
        }
	</style>
</head>
<body>
	
    <!-- 컨텐츠 영역 -->
    <div class="container">
        <h1>거래 내역</h1>

		<!-- 계좌 선택 -->
		<div class="account-select-container">
			<label for="account-select"><strong>계좌 선택</strong></label>
			<select id="account-select">
				<option value="">계좌 선택</option>
				<!-- 계좌 목록은 Ajax로 동적으로 로드 -->
			</select>
		</div>

		<!-- 거래 내역 테이블 -->
		<h2>거래 내역</h2>
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
				<!-- 거래 내역은 Ajax로 동적으로 로드 -->
			</tbody>
		</table>
	</div>

	<!-- Ajax Script -->
	<script>
	$(document).ready(function () {
	    // 계좌 목록 불러오기
	    $.ajax({
	        url: "/testWeb/bank/accounts", // AccountServlet 경로
	        type: "GET",
	        success: function (response) {
	            // 응답 데이터를 분석하여 드롭다운 옵션 추가
	            $("#account-select").html('<option value="">계좌 선택</option>'); // 기본 옵션 추가
	        	console.log(response); // 응답 데이터를 확인
	            $(response).find("tbody tr").each(function () {
	                var accountNo = $(this).find("td:nth-child(1)").text().trim(); // 계좌 번호
	                var balance = $(this).find("td:nth-child(2)").text().trim();   // 잔액

	                // 잔액 데이터에 "(원)"이 이미 포함되어 있는지 확인
	                if (!balance.endsWith("원")) {
	                    balance += "원"; // "원"이 없으면 추가
	                }

	                // 드롭다운 옵션 추가
	                $("#account-select").append('<option value="' + accountNo + '">' + accountNo + ' (' + balance + ')</option>');
	            });
	        },
	        error: function () {
	            alert("계좌 목록을 불러오는 데 실패했습니다.");
	        }
	    });

	    // 계좌 선택 시 거래 내역 불러오기
	    $("#account-select").change(function () {
	        var accountNo = $(this).val();
	        if (accountNo) {
	            $.ajax({
	                url: "/testWeb/bank/transactions",
	                type: "GET",
	                data: { accountNo: accountNo },
	                success: function (response) {
	                    $("#transaction-table tbody").html(response);
	                },
	                error: function () {
	                    alert("거래내역을 불러오는 데 실패했습니다.");
	                }
	            });
	        } else {
	            $("#transaction-table tbody").html("");
	        }
	    });
	});

    </script>
</body>
</html>