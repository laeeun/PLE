<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 정보 수정</title>

    <!-- 감성 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <!-- Bootstrap -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
        }

        h3 {
            color: #ff69b4;
        }

        .form-label {
            font-weight: bold;
            color: #ff69b4;
        }

        .btn-primary {
            background-color: #ff99cc;
            border-color: #ff99cc;
        }

        .btn-primary:hover {
            background-color: #ff66b2;
            border-color: #ff66b2;
        }

        .btn-outline-secondary:hover {
            background-color: #f8d7da;
        }

        .card {
            border: 1px solid #ffcce0;
            border-radius: 12px;
            padding: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <h3 class="text-center mb-4">🔧 전문가 정보 수정</h3>

                <form action="<c:url value='/expert/update' />" method="post">
                    <input type="hidden" name="expert_board_id" value="${expert.expert_board_id}" />
                    <input type="hidden" name="expert_id" value="${expert.expert_id}" />

                    <div class="mb-3">
                        <label class="form-label">제목</label>
                        <input type="text" name="title" class="form-control" value="${expert.title}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">내용</label>
                        <textarea name="content" class="form-control" rows="5" required>${expert.content}</textarea>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">카테고리</label>
                        <select name="category" class="form-select" required>
                            <option value="디자인" ${expert.category == '디자인' ? 'selected' : ''}>디자인</option>
                            <option value="프로그래밍" ${expert.category == '프로그래밍' ? 'selected' : ''}>프로그래밍</option>
                            <option value="번역" ${expert.category == '번역' ? 'selected' : ''}>번역</option>
                            <option value="음악" ${expert.category == '음악' ? 'selected' : ''}>음악</option>
                            <option value="영상편집" ${expert.category == '영상편집' ? 'selected' : ''}>영상편집</option>
                            <option value="글쓰기" ${expert.category == '글쓰기' ? 'selected' : ''}>글쓰기</option>
                            <option value="과외" ${expert.category == '과외' ? 'selected' : ''}>과외</option>
                            <option value="생활서비스" ${expert.category == '생활서비스' ? 'selected' : ''}>생활서비스</option>
                            <option value="기획창작" ${expert.category == '기획창작' ? 'selected' : ''}>기획/창작</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">가격 (원)</label>
                        <input type="number" name="price" class="form-control" value="${expert.price}" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">예약 가능 시간 <small>(예: 월 14:00, 화 16:00)</small></label>
                        <input type="text" name="available_time" class="form-control" value="${expert.available_time}" required>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="<c:url value='/expert/view?id=${expert.expert_board_id}' />" class="btn btn-outline-secondary">← 돌아가기</a>
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
