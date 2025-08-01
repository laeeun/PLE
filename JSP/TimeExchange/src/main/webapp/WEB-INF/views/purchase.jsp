<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>재능 구매 페이지</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body>

    <!-- 에러 메시지 혹은 성공 메시지가 있을 경우 alert로 출력 -->
    <c:if test="${not empty errorMessage}">
        <script>
            alert("${errorMessage}");
        </script>
    </c:if>
    <c:if test="${not empty successMessage}">
        <script>
            alert("${successMessage}");
        </script>
    </c:if>

    <div class="container mt-5">
        <h2 class="mb-4">🛒 재능 구매 정보</h2>

        <table class="table table-bordered">
            <tr>
                <th>판매자 ID</th>
                <td>${talent.member_id}</td>
            </tr>
            <tr>
                <th>카테고리</th>
                <td>${talent.category}</td>
            </tr>
            <tr>
                <th>시간 슬롯</th>
                <td>${talent.timeSlotDisplay} (${talent.timeSlot}분)</td>
            </tr>
        </table>

        <div class="mt-4">
            <a href="<c:url value='/talent' />" class="btn btn-secondary me-2">❌ 구매 취소</a>

            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <form action="<c:url value='/purchase/request' />" method="post" class="d-inline">
                        <input type="hidden" name="talent_id" value="${talent.talent_id}" />
                        <input type="hidden" name="seller_id" value="${talent.member_id}" />
                        <button type="submit" class="btn btn-success">✅ 구매 요청</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <span class="text-danger">※ 로그인 후 구매 요청이 가능합니다.</span>
                    <a href="<c:url value='/login' />" class="btn btn-outline-primary btn-sm ms-2">로그인하기</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

    <jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
