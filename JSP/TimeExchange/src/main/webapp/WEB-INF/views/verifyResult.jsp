<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이메일 인증 결과</title>
    <!-- Pretendard + FontAwesome -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" />

    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }

        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px;
        }

        .result-box {
            width: 100%;
            max-width: 480px;
            background: var(--surface-alt);
            border: 1px solid var(--border);
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
            padding: 40px 35px;
            text-align: center;
        }

        .result-box .icon {
            font-size: 48px;
            margin-bottom: 20px;
        }

        .result-box.success .icon {
            color: #10b981; /* Green */
        }

        .result-box.error .icon {
            color: #ef4444; /* Red */
        }

        .result-box h2 {
            color: var(--primary);
            font-weight: 800;
            font-size: 24px;
            margin-bottom: 10px;
        }

        .result-box p {
            color: var(--text-sub);
            font-size: 16px;
            margin-bottom: 30px;
        }

        .btn-home {
            padding: 12px 24px;
            background: var(--accent);
            color: white;
            font-weight: 600;
            border: none;
            border-radius: 12px;
            box-shadow: 0 0 12px rgba(255, 107, 53, 0.3);
            transition: all 0.25s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-home:hover {
            background-color: #ff5a1f;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>

<div class="result-box ${message.contains('완료') ? 'success' : 'error'}">
    <div class="icon">
        <i class="fas ${message.contains('완료') ? 'fa-circle-check' : 'fa-circle-xmark'}"></i>
    </div>
    <h2>인증 결과</h2>
    <p>${message}</p>
    <a href="<c:url value='/' />" class="btn-home">홈으로 돌아가기</a>
</div>

</body>
</html>
