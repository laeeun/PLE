<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>

    <!-- ✅ 감성 폰트 Pretendard -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5); /* 연핑크 그라데이션 */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            animation: fadeIn 2s ease-in;
        }

        .login-container {
            background: #fff;
            padding: 40px 30px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            width: 320px;
            animation: popUp 1s ease-in-out;
        }

        .login-container h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #ff69b4;
        }

        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-top: 12px;
            border: 1px solid #ddd;
            border-radius: 10px;
            font-size: 15px;
        }

        .login-container button {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background: #ff99cc;
            border: none;
            color: white;
            border-radius: 10px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.3s ease;
        }

        .login-container button:hover {
            background: #ff66b2;
            transform: scale(1.05);
        }

        .links {
            text-align: center;
            margin-top: 20px;
        }

        .links a {
            margin: 0 6px;
            font-size: 0.9em;
            color: #555;
            text-decoration: none;
            transition: color 0.3s;
        }

        .links a:hover {
            text-decoration: underline;
            color: #ff69b4;
        }

        .error {
            color: red;
            text-align: center;
            font-size: 0.9em;
            margin-top: 10px;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes popUp {
            0% { transform: scale(0.9); opacity: 0; }
            100% { transform: scale(1); opacity: 1; }
        }
    </style>
</head>
<body>

<div class="login-container">
	
    <h2>어서오세요!</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="member_id" placeholder="아이디" required />
        <input type="password" name="pw" placeholder="비밀번호" required />
        <button type="submit">로그인</button>
        <%
            System.out.println("로그인완료");
        %>
    </form>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <div class="links">
        <a href="${pageContext.request.contextPath}/findId">아이디 찾기</a> |
        <a href="${pageContext.request.contextPath}/findPw">비밀번호 찾기</a> |
        <a href="${pageContext.request.contextPath}/signUp">회원가입</a> |
        <a href="${pageContext.request.contextPath}/">홈</a>
    </div>
    
    <div style="text-align: center; margin-top: 30px;">
    	<jsp:include page="/WEB-INF/views/footer.jsp" />
	</div>	   
</div>

</body>
</html>
