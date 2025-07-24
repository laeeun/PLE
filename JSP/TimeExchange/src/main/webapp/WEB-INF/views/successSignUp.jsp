<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입 완료</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .success-box {
            width: 100%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            padding: 40px 35px;
            text-align: center;
        }

        .success-box h2 {
            color: #7e22ce;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .success-box p {
            color: #4c1d95;
            font-size: 16px;
            margin-bottom: 25px;
        }

        .btn-home {
            padding: 12px 24px;
            background: linear-gradient(to right, #a855f7, #ec4899);
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 12px;
            box-shadow: 0 0 14px rgba(168, 85, 247, 0.4);
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(168, 85, 247, 0.5);
        }
    </style>
</head>
<body>

<div class="success-box">
    <h2>🎉 회원가입 완료!</h2>
    <p>가입해주셔서 감사합니다.</p>
    <p>입력하신 이메일 주소로 인증 메일을 보냈습니다.<br>
       메일을 확인하시고 인증을 완료해 주세요.</p>
    <a href="<c:url value='/' />" class="btn-home">홈으로 돌아가기</a>
</div>

</body>
</html>
