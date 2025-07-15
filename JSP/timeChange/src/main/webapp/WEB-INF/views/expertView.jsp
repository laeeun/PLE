<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 상세보기</title>

    <!-- 감성 폰트 Pretendard -->
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

        .btn-primary {
            background-color: #ff99cc;
            border-color: #ff99cc;
        }

        .btn-primary:hover {
            background-color: #ff66b2;
            border-color: #ff66b2;
        }

        .btn-outline-primary {
            color: #ff69b4;
            border-color: #ff69b4;
        }

        .btn-outline-primary:hover {
            background-color: #ff69b4;
            color: white;
        }

        .card {
            border: 1px solid #ffcce0;
            border-radius: 12px;
            padding: 20px;
        }

        .label {
            font-weight: bold;
            color: #ff66b2;
        }

        .value {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <h3 class="text-center mb-4">💼 전문가 상세보기</h3>

                <div class="mb-3">
                    <div class="label">제목:</div>
                    <div class="value">${expert.title}</div>
                </div>

                <div class="mb-3">
                    <div class="label">내용:</div>
                    <div class="value">${expert.content}</div>
                </div>

                <div class="mb-3">
                    <div class="label">카테고리:</div>
                    <div class="value">${expert.category}</div>
                </div>

                <div class="mb-3">
                    <div class="label">가격:</div>
                    <div class="value">${expert.price} 원</div>
                </div>

                <div class="mb-3">
                    <div class="label">예약 가능 시간:</div>
                    <div class="value">${expert.available_time}</div>
                </div>

                <div class="mb-3">
                    <div class="label">작성자:</div>
                    <div class="value">${expert.expert_id}</div>
                </div>

                <div class="mb-3">
                <div class="label">등록일:</div>
                <div class="value">${expert.formattedCreatedAt}</div>
            </div>

                <!-- 버튼 영역 (작성자만 노출) -->
                <c:if test="${sessionScope.loggedInUser.member_id == expert.expert_id}">
                    <div class="d-flex justify-content-end">
                        <a href="<c:url value='/expert/update?id=${expert.expert_board_id}' />" class="btn btn-outline-primary me-2">수정</a>
                        <a href="<c:url value='/expert/delete?id=${expert.expert_board_id}' />" class="btn btn-primary"
                           onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                    </div>
                </c:if>

                <div class="mt-3 text-end">
                    <a href="<c:url value='/expert' />" class="btn btn-outline-secondary">← 목록으로</a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
