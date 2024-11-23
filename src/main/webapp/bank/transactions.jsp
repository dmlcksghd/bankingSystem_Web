<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>거래 내역</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>거래 내역 페이지</h1>

	<!-- 계좌 선택 -->
	<label for="account-select">계좌 선택</label>
	<select id="account-select">
		<option>계좌 선택</option>
		<!-- 계좌 목록은 Ajax로 동적으로 로드 -->
	</select>

	<!-- 거래 내역 테이블 -->
	<h2>거래 내역</h2>
	<table border="1" id="transaction-table">
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

	<script>
	    $(document).ready(function () {
	        // 계좌 목록 불러오기
	        $.ajax({
	            url: "/testWeb/bank/accounts", // AccountServlet 경로
	            type: "GET",
	            success: function (response) {
	                // 응답 데이터를 분석하여 드롭다운 옵션 추가
	                var accounts = $(response).find("tbody tr");
	                $("#account-select").html('<option value="">계좌 선택</option>'); // 기본 옵션 추가
	                accounts.each(function () {
	                    var accountNo = $(this).find("td:nth-child(1)").text();
	                    var balance = $(this).find("td:nth-child(2)").text();
	                    $("#account-select").append('<option value="' + accountNo + '">' + accountNo + ' (' + balance + '원)</option>');
	                });
	            },
	            error: function () {
	                alert("계좌 목록을 불러오는 데 실패했습니다.");
	            }
	        });
	    });

    		
    	    // 계좌 선택 시 거래 내역 불러오기
    	    $("#account-select").change(function() {
        		var accountNo = $(this).val();
        		if (accountNo) {
        			$.ajax({
        				url: "/testWeb/bank/transactions",
        				type: "GET",
        				data: { accountNo: accountNo },
        				success: function(response) {
        					$("#transaction-table tbody").html(response);
        				},
        				error: function() {
        					alert("거래내역을 불러오는 데 실패했습니다.");
        				}
        			});
        		} else {
        			$("#transaction-table tbody").html("");
        		  }
    	    });
    </script>
</body>
</html>