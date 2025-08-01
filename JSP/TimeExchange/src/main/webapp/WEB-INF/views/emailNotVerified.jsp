<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이메일 인증 필요</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fbcfe8, #e0e7ff, #f3e8ff, #fce7f3);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .error-box {
            max-width: 450px;
            width: 90%;
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            padding: 40px 30px;
            text-align: center;
        }

        .error-box h2 {
            font-size: 24px;
            color: #dc2626;
            margin-bottom: 20px;
        }

        .error-box p {
            color: #4b5563;
            font-size: 16px;
            margin-bottom: 30px;
        }

        .btn-home {
            padding: 10px 20px;
            background: linear-gradient(to right, #f87171, #fbbf24);
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            text-decoration: none;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
        }
    </style>
</head>
<body>

<div class="error-box">
    <h2>⛔ 이메일 인증이 필요합니다</h2>
    <p>로그인을 완료하려면 이메일 인증을 먼저 진행해주세요.</p>
    
    <!-- 인증 메일 재전송 폼 -->
    <form action="<c:url value='/mail/resend' />" method="post" style="margin-bottom: 15px;">
        <input type="hidden" name="member_id" value="${member.member_id}" />
        <button type="submit" class="btn-home">인증 메일 다시 보내기</button>
    </form>
    
    <a href="<c:url value='/' />" class="btn-home">홈으로 가기</a>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />


</body>
</html>
