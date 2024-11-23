<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>카드 목록</title>
</head>
<body>
    <h1>카드 목록</h1>
    <table border="1">
        <thead>
            <tr>
                <th>카드 번호</th>
                <th>연결된 계좌</th>
                <th>한도</th>
                <th>발급일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="card" items="${cards}">
                <tr>
                    <td>${card.cardNo}</td>
                    <td>${card.accountNo}</td>
                    <td>${card.limitAmount}</td>
                    <td>${card.issuedAt}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
