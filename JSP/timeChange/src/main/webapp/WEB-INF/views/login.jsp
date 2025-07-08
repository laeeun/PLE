<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 300px;
        }
        .login-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .login-container button {
            width: 100%;
            padding: 10px;
            margin-top: 20px;
            background: #007bff;
            border: none;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
        .login-container button:hover {
            background: #0056b3;
        }
        .links {
            text-align: center;
            margin-top: 15px;
        }
        .links a {
            margin: 0 5px;
            font-size: 0.9em;
            color: #333;
            text-decoration: none;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
            text-align: center;
            font-size: 0.9em;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>로그인</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="userId" placeholder="아이디" required />
        <input type="password" name="pw" placeholder="비밀번호" required />
        <button type="submit">로그인</button>
    </form>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <div class="links">
        <a href="${pageContext.request.contextPath}/findId">아이디 찾기</a> |
        <a href="${pageContext.request.contextPath}/findPw">비밀번호 찾기</a> |
        <a href="${pageContext.request.contextPath}/signUp">회원가입</a>
    </div>
</div>

</body>
</html>
