<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 구매</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }

        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            margin: 0;
            padding: 0;
            color: var(--text-main);
        }

        .container {
            max-width: 700px;
            margin: 60px auto;
            background: var(--surface-alt);
            border-radius: 16px;
            padding: 40px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.06);
            border: 1px solid var(--border);
        }

        h2 {
            font-size: 24px;
            font-weight: 700;
            color: var(--primary);
            text-align: center;
            margin-bottom: 30px;
        }

        .table {
            background-color: white;
        }

        th {
            width: 30%;
            background: #f5f5f5;
            color: var(--primary);
        }

        .btn-success {
            background-color: var(--accent);
            border: none;
        }

        .btn-success:hover {
            background-color: #e85c26;
        }

        .btn-secondary {
            background-color: #6c757d;
            border: none;
        }

        .text-danger {
            margin-left: 10px;
            font-size: 14px;
            color: #dc3545;
        }

        .btn-outline-primary {
            font-size: 14px;
            padding: 6px 12px;
        }

        .footer {
            margin-top: 50px;
        }
    </style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/nav.jsp" />
    <!-- ✅ 알림 메시지 -->
    <c:if test="${not empty errorMessage}">
        <script>alert("${errorMessage}");</script>
    </c:if>
    <c:if test="${not empty successMessage}">
        <script>alert("${successMessage}");</script>
    </c:if>

    <!-- ✅ 구매 정보 카드 -->
    <div class="container">
        <h2><i class="fas fa-shopping-cart"></i> 재능 구매 정보</h2>

        <table class="table table-bordered mt-4">
            <tr>
                <th>판매자</th>
                <td>${talent.username}</td>
            </tr>
            <tr>
                <th>카테고리</th>
                <td>${talent.category}</td>
            </tr>
            <tr>
                <th>거래 시간</th>
                <td>${talent.timeSlotDisplay} (${talent.timeSlot}분)</td>
            </tr>
        </table>
		<div class="talent-overview mb-4 text-center">
		    <h3 style="font-size: 22px; font-weight: bold; color: var(--primary); margin-bottom: 10px;">
		        ${talent.title}
		    </h3>
		    
		    <p style="font-size: 15px; color: var(--text-sub); max-width: 600px; margin: 0 auto 8px;">
		        ${talent.description}
		    </p>
		
		    <div style="font-size: 16px; color: #FFC107;">
		        <c:forEach begin="1" end="5" var="i">
		            <i class="fa${i <= talent.averageRating ? 's' : 'r'} fa-star"></i>
		        </c:forEach>
		        <span style="font-size: 14px; color: var(--text-sub); margin-left: 6px;">
		            ${talent.averageRating} / 5.0
		        </span>
		    </div>
		</div>
        <!-- ✅ 구매 요청 or 로그인 -->
        <div class="mt-4 text-center">
            <a href="<c:url value='/talent' />" class="btn btn-secondary me-2" id="cancelBtn">
			    <i class="fas fa-times-circle"></i> 구매 취소
			</a>

            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <form action="<c:url value='/purchase/request' />" method="post" class="d-inline">
                        <input type="hidden" name="talent_id" value="${talent.talent_id}" />
                        <input type="hidden" name="seller_id" value="${talent.member_id}" />
                        <button type="submit" class="btn btn-success" id="requestBtn">
						    <i class="fas fa-check-circle"></i> 구매 요청
						</button>
                    </form>
                    <c:if test="${sessionScope.loggedInUser.account lt talent.timeSlot}">
					    <div style="margin-top: 20px; padding: 15px; background-color: #fff4e6; border: 1px solid #f0b37e; border-radius: 8px; color: #cc5c00;">
					        ⚠️ 현재 보유 시간(${sessionScope.loggedInUser.account}분)이 부족합니다.<br/>
					        <a href="<c:url value='/charge' />" style="color: var(--accent); text-decoration: underline;">시간 충전소로 이동</a>
					    </div>
					</c:if>
                </c:when>
                <c:otherwise>
                    <span class="text-danger">※ 로그인 후 구매 요청이 가능합니다.</span>
                    <a href="<c:url value='/login' />" class="btn btn-outline-primary ms-2">로그인하기</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<script>
	//✅ 구매 취소 버튼 클릭 시 confirm
	document.getElementById("cancelBtn")?.addEventListener("click", function (e) {
	    e.preventDefault(); // 기본 이동 막기
	    if (confirm("정말로 구매를 취소하시겠습니까?")) {
	        window.location.href = this.getAttribute("href");
	    }
	});
	
	// ✅ 구매 요청 버튼 클릭 시 confirm
	document.getElementById("requestBtn")?.addEventListener("click", function (e) {
	    e.preventDefault(); // 기본 submit 막기
	    if (confirm("정말로 이 재능을 구매 요청하시겠습니까?")) {
	        this.closest("form").submit(); // 폼 전송
	    }
	});
</script>
</body>
</html>
