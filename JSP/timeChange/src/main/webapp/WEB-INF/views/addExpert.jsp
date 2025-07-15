<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 등록</title>

    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        h3 {
            color: #9333ea;
            font-weight: bold;
            text-align: center;
            animation: fadeSlide 1s ease forwards;
        }

        @keyframes fadeSlide {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .form-control,
        .form-select {
            background: rgba(255, 255, 255, 0.6);
            border: none;
            border-radius: 14px;
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 10px rgba(168, 85, 247, 0.08);
        }

        .form-control:focus,
        .form-select:focus {
            border: none;
            box-shadow: 0 0 0 0.2rem rgba(168, 85, 247, 0.25);
        }

        .btn-gradient {
            background: linear-gradient(to right, #a855f7, #ec4899);
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 10px;
            box-shadow: 0 0 12px rgba(168, 85, 247, 0.3);
            position: relative;
            overflow: hidden;
            padding: 10px 24px;
            transition: all 0.3s ease;
        }

        .btn-gradient:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(168, 85, 247, 0.4);
        }

        .btn-gradient::after {
            content: "";
            position: absolute;
            top: 0;
            left: -75%;
            width: 50%;
            height: 100%;
            background: linear-gradient(to right, rgba(255,255,255,0.3), rgba(255,255,255,0));
            transform: skewX(-20deg);
            animation: shine 2.5s infinite;
        }

        @keyframes shine {
            0% { left: -75%; }
            100% { left: 125%; }
        }

        .btn-outline-secondary {
            border-radius: 10px;
            font-weight: 600;
            padding: 10px 24px;
            border: 2px solid #9333ea;
            color: #9333ea;
            background-color: transparent;
            transition: 0.3s ease;
        }

        .btn-outline-secondary:hover {
            background-color: #ede9fe;
            color: #6b21a8;
        }

        .form-label {
            font-weight: 600;
            color: #6b21a8;
        }

        form {
            background: rgba(255, 255, 255, 0.65);
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.06);
            backdrop-filter: blur(12px);
            animation: fadeInUp 0.7s ease both;
        }

        @keyframes fadeInUp {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h3 class="mb-4">📝 전문가 재능 등록</h3>

            <form action="<c:url value='/expert/add' />" method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>

                <div class="mb-3">
                    <label for="content" class="form-label">내용</label>
                    <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
                </div>

                <div class="mb-3">
                    <label for="category" class="form-label">카테고리</label>
                    <select class="form-select" id="category" name="category" required>
                        <option value="" disabled selected>선택하세요</option>
                        <option value="디자인">디자인</option>
                        <option value="프로그래밍">프로그래밍</option>
                        <option value="마케팅">마케팅</option>
                        <option value="상담">상담</option>
                        <option value="기획">기획</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="price" class="form-label">가격 (원)</label>
                    <input type="number" class="form-control" id="price" name="price" min="0" required>
                </div>

                <div class="mb-3">
                    <label for="available_time" class="form-label">예약 가능 시간 (예: 월 14:00, 화 15:00)</label>
                    <input type="text" class="form-control" id="available_time" name="available_time" required>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-gradient">등록하기</button>
                    <a href="<c:url value='/expert' />" class="btn btn-outline-secondary ms-2">취소</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>