<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <%@ include file="header.jsp" %>
    <%@ include file="menu.jsp" %> <!-- 메뉴 포함 -->
<title>계정 정보 변경</title>
<!-- CSS 외부 연결 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<style>
/* 공통 스타일 */
body {
    font-family: 'Arial', sans-serif;
    background-color: #F7F9FC;
    color: #333;
    margin: 0;
    padding: 0;
}

/* 컨테이너 스타일 */
.container {
    max-width: 500px;
    margin: 50px auto;
    background-color: #FFFFFF;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 30px;
    text-align: center;
}

/* 제목 스타일 */
.main-title {
    font-size: 24px;
    font-weight: bold;
    color: #007AFF;
    margin-bottom: 20px;
}

/* 폼 스타일 */
.form-container {
    margin-bottom: 20px;
    text-align: left;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    font-size: 14px;
    margin-bottom: 5px;
    color: #555;
}

.form-group input {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #F9FAFB;
    box-sizing: border-box;
}

.form-group input:focus {
    outline: none;
    border-color: #007AFF;
    box-shadow: 0 0 5px rgba(0, 122, 255, 0.3);
    background-color: #FFFFFF;
}

/* 버튼 스타일 */
button {
    display: inline-block;
    width: 100%;
    padding: 10px;
    font-size: 16px;
    color: #FFFFFF;
    background-color: #007AFF;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #005BBB;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
    .container {
        padding: 20px;
    }

    button {
        font-size: 14px;
    }
}

</style>
</head>
<body> 
	<div class="container">
	<h1 class="main-title">계정 정보 변경</h1>
	
	<!-- 아이디 변경 폼 -->
	<form class="form-container" action="${pageContext.request.contextPath}/bank/auth.do" method="POST">
		<div class="form-group">
	        <label for="newUserId">새 아이디:</label>
	        <input type="hidden" name="action" value="updateUserId">
	        <input type="text" id="newUserId" name="newUserId" placeholder="새로운 아이디 입력">
	        <button type="submit">아이디 변경</button>
        </div>
    </form>

    <!-- 비밀번호 변경 폼 -->
    <form class="form-container" action="${pageContext.request.contextPath}/bank/auth.do" method="POST">
    	<div class="form-group">
	        <input type="hidden" name="action" value="updatePassword">
	        <label for="newPassword">새 비밀번호:</label>
	        <input type="password" id="newPassword" name="newPassword" placeholder="새로운 비밀번호 입력">
	        <button type="submit">비밀번호 변경</button>
        </div>
    </form>
	</div>
</body>
</html>
