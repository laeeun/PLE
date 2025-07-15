<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${talent.title} - 상세보기</title>

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

        .card {
            border: 1px solid #ffcce0;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(255, 105, 180, 0.1);
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
            background-color: #ffcce0;
            color: black;
        }

        .btn-warning, .btn-danger {
            margin-left: 5px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <div class="card">
        <div class="card-header" style="background-color: #ff99cc;">
   			 <h3 class="text-white">${talent.title}</h3>
		</div>
        <div class="card-body">
        	<p><strong>🧑‍💻 작성자:</strong><a href="<c:url value='/profile?id=${talent.member_id}' />">${talent.member_id}</a></p>
            <p><strong>📂 카테고리:</strong> ${talent.category}</p>
            <p><strong>🕒 판매 시간:</strong> ${talent.timeSlotDisplay}</p>
            <p><strong>📅 등록일:</strong>
                <fmt:formatDate value="${createdDate}" pattern="yyyy-MM-dd HH:mm" />
            </p>
            <hr>
            <p><strong>📌 설명:</strong></p>
            <p>${talent.description}</p>
        </div>
        <div class="card-footer d-flex justify-content-between align-items-center">
            <div>
                <a href="<c:url value='/talent' />" class="btn btn-outline-secondary">← 목록으로</a>
            </div>
            <div>
    <!-- 내가 등록한 재능이면 수정/삭제 버튼 표시 -->
			    <c:if test="${sessionScope.loggedInUser != null 
			                 && sessionScope.loggedInUser.member_id == talent.member_id}">
			        <a href="<c:url value='/talent/update?id=${talent.talent_id}' />" class="btn btn-warning">✏️ 수정</a>
			        <a href="<c:url value='/talent/delete?id=${talent.talent_id}' />"
			           class="btn btn-danger"
			           onclick="return confirm('정말 삭제하시겠습니까?')">🗑️ 삭제</a>
			    </c:if>
			
			    <!-- 로그인 상태 + 내가 등록하지 않은 재능이면 구매하기 버튼 표시 -->
			    <c:if test="${sessionScope.loggedInUser != null 
			                 && sessionScope.loggedInUser.member_id != talent.member_id}">
			        <a href="<c:url value='/purchase?id=${talent.talent_id}' />" class="btn btn-primary">
			            💰 구매하기
			        </a>
			    </c:if>
			
			    <!-- 로그인하지 않았을 경우: alert 처리 -->
			    <c:if test="${sessionScope.loggedInUser == null}">
			        <a href="javascript:void(0);" class="btn btn-primary"
			           onclick="alert('로그인 후 사용 가능한 기능입니다.'); location.href='<c:url value="/login" />';">
			            💰 구매하기
			        </a>
			    </c:if>
			</div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
