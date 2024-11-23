<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="header.jsp"%>
    <title>로그인</title>
    
    <!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="../css/login.css">
    
    <style>
	    body {
	    font-family: 'Arial', sans-serif;
	    background-color: #F7F9FC; /* Toss 스타일의 밝은 배경색 */
	    color: #333; /* 텍스트 기본 색상 */
	    margin: 0;
	    padding: 0;
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 100vh;
		}

		.container {
		    background-color: #FFFFFF; /* 카드형 배경 */
		    border-radius: 10px;
		    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
		    padding: 40px 30px;
		    width: 350px;
		    text-align: center;
		}
		
		.container h1 {
		    font-size: 24px;
		    color: #007AFF; /* Toss 스타일의 주요 색상 */
		    margin-bottom: 20px;
		    font-weight: bold;
		}
		
		.form-group {
		    margin-bottom: 20px;
		    text-align: left;
		}
		
		label {
		    font-size: 14px;
		    color: #555;
		    margin-bottom: 5px;
		    display: block;
		}
		
		input[type="text"],
		input[type="password"] {
		    width: 100%;
		    padding: 10px;
		    border: 1px solid #ddd;
		    border-radius: 5px;
		    font-size: 14px;
		    background-color: #F9FAFB;
		    transition: all 0.3s ease;
		}
		
		input[type="text"]:focus,
		input[type="password"]:focus {
		    border-color: #007AFF;
		    outline: none;
		    background-color: #FFFFFF;
		    box-shadow: 0 0 5px rgba(0, 122, 255, 0.3);
		}
		
		button {
		    width: 100%;
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
		
		button:hover {
		    background-color: #005BBB; /* 버튼 hover 색상 */
		}
		
		.error-message {
		    color: #FF4D4D;
		    font-size: 14px;
		    margin-bottom: 20px;
		}
		
		a {
		    color: #007AFF;
		    text-decoration: none;
		    font-size: 14px;
		}
		
		a:hover {
		    text-decoration: underline;
		}
    </style>
</head>
<body>
	<div class="container">
    	<h1>로그인</h1>
	    <form action="${path}/bank/auth.do" method="post">
	        <input type="hidden" name="action" value="login"> <!-- 로그인 페이지 액션 -->
	        
	        <!-- 아이디 입력 -->
	        <div class="form-group">
		        <label for="userId">아이디</label>
		        <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요." required>
	        </div>
	       
	        <!-- 비밀번호 입력 -->
	        <div class="form-group">
		        <label for="password">비밀번호</label>
		        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요." required>
	        </div>
	        
	        <!-- 에러 메시지 -->
	        <c:if test="${not empty errorMessage}">
	        	<div class="error-message">${errorMessage}</div>
	        </c:if>
	        
	        <!-- 로그인 버튼 -->
	        <button type="submit">로그인</button>
	        
	        <!-- 회원가입 링크 -->
	    	<p>계정이 없으신가요? <a href="register.jsp">회원가입</a></p>
	    </form>
    </div>
</body>
</html>