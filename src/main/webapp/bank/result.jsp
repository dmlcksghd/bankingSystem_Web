<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>회원가입 결과</title>
	<!-- 외부 스타일시트 연결 -->
    <link rel="stylesheet" type="text/css" href="${path}/css/result.css">
    
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
		
		.container p {
		    font-size: 16px;
		    color: #555;
		    margin-bottom: 20px;
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
    <div class="container">
        <h1>${message}</h1>
        <p>계속 진행하려면 아래 버튼을 클릭하세요.</p>
        <a href="${redirect}">
            <button>돌아가기</button>
        </a>
    </div>
</body>
</html>