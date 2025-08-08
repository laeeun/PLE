<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>

    <!-- 감성 폰트 Pretendard -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />
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
		
		a {
		    text-decoration: none;
		    color: var(--primary); /* 진한 텍스트 색상 */
		    transition: color 0.2s ease, text-decoration 0.2s ease;
		}
		
		
		body {
		    font-family: 'Pretendard', sans-serif;
		    background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
		    background-size: 400% 400%;
		    animation: gradientBG 15s ease infinite;
		    color: var(--text-main);
		}
		
		@keyframes gradientBG {
		    0% { background-position: 0% 50%; }
		    50% { background-position: 100% 50%; }
		    100% { background-position: 0% 50%; }
		}
		
		.card {
		    background: rgba(255,255,255,0.85);
		    backdrop-filter: blur(10px);
		    border: 1px solid var(--border);
		    border-radius: 16px;
		    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
		}
		
		.card-header {
		    background-color: var(--primary);
		    color: white;
		    padding: 16px 20px;
		    font-weight: bold;
		    border-top-left-radius: 16px;
		    border-top-right-radius: 16px;
		}
		
		.card-body p {
		    margin: 0.5rem 0;
		    font-size: 15px;
		    color: var(--text-main);
		}
		
		.card-body strong {
		    color: var(--accent);
		}
		
		.profile-image {
		    width: 110px;
		    height: 110px;
		    object-fit: cover;
		    border-radius: 50%;
		    border: 4px solid white;
		    margin: 0 auto 16px;
		    display: block;
		    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
		}
		
		.custom-btn {
		    padding: 10px 18px;
		    font-size: 14px;
		    font-weight: 600;
		    border-radius: 8px;
		    border: none;
		    text-decoration: none;
		    transition: 0.2s;
		    display: inline-block;
		    color: white;
		}
		
		.btn-edit {
		    background: var(--accent);
		}
		
		.btn-delete {
		    background: #EF4444;
		}
		
		.btn-outline {
		    background-color: white;
		    border: 2px solid var(--accent);
		    color: var(--primary);
		}
		
		.custom-btn:hover {
		    transform: translateY(-2px);
		    opacity: 0.9;
		}
		
		.btn-group-wrap {
		    display: flex;
		    flex-wrap: wrap;
		    gap: 12px;
		    margin-top: 28px;
		}
		
		.info-table {
		    margin-top: 10px;
		    background-color: var(--surface-alt);
		    border-radius: 12px;
		    overflow: hidden;
		    font-size: 15px;
		}
		
		.info-table th {
		    background-color: var(--primary);
		    color: white;
		    width: 150px;
		    padding: 12px;
		    vertical-align: middle;
		    text-align: left;
		}
		
		.info-table td {
		    background-color: #ffffff;
		    color: var(--text-main);
		    padding: 12px;
		    vertical-align: middle;
		}
		
	    .expert-wrapper {
	        background-color: var(--expert-box-bg);
	        border-radius: 16px;
	        padding: 24px;
	        margin-top: 50px;
	        box-shadow: inset 0 0 6px rgba(0,0,0,0.05);
	    }
	    .talent-carousel {
	        display: flex;
	        overflow-x: auto;
	        gap: 16px;
	        scroll-snap-type: x mandatory;
	        padding: 20px 0;
	    }

	    .talent-card {
	        flex: 0 0 auto;
	        scroll-snap-align: start;
	        background: rgba(255, 255, 255, 0.9);
	        border: 1px solid var(--border);
	        border-radius: 16px;
	        box-shadow: 0 4px 12px rgba(0,0,0,0.06);
	        padding: 20px;
	        width: 320px;
	        min-height: 220px;
	        position: relative;
	        transition: transform 0.2s ease;
	    }
	
	    .talent-card:hover {
	        transform: translateY(-4px);
	    }
	
	    .talent-card h5 {
	        font-size: 18px;
	        font-weight: 600;
	        margin-bottom: 10px;
	        color: var(--primary);
	    }
	
	    .talent-card p {
	        margin: 6px 0;
	        font-size: 14px;
	        color: var(--text-main);
	    }
	
	    .talent-card .rating {
	        color: #facc15;
	        font-weight: bold;
	    }
	
	    /* 가로 스크롤바 스타일 */
	    .talent-carousel::-webkit-scrollbar {
	        height: 8px;
	    }
	
	    .talent-carousel::-webkit-scrollbar-thumb {
	        background-color: var(--accent);
	        border-radius: 4px;
	    }
	
	    .talent-carousel::-webkit-scrollbar-track {
	        background-color: #f1f1f1;
	    }
	    .talent-card-link {
		    text-decoration: none;
		    color: inherit;
		}
		
		.talent-card-link:hover {
		    text-decoration: none;
		}
    </style>
</head>
<body>
<c:if test="${not empty message}">
    <script>alert('${fn:escapeXml(message)}');</script>
</c:if>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5 mb-5">
    <!-- ✅ 기본 정보 카드 -->
    <div class="card">
        <div class="card-header">
            <h4><i class="fas fa-user-circle me-2"></i> 마이페이지</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${member.profileImage ne null and member.profileImage ne 'default-profile.png' and fn:length(member.profileImage) > 0}">
                    <img src="${pageContext.request.contextPath}/upload/profile/${member.profileImage}" alt="프로필 이미지" class="profile-image" />
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/resources/images/default-profile.png' />" alt="기본 이미지" class="profile-image" />
                </c:otherwise>
            </c:choose>

            <h5 class="mb-3"><i class="fas fa-id-badge me-2"></i> 기본 정보</h5>
            <table class="table info-table">
                <tbody>
                    <tr><th>아이디</th><td>${member.member_id}</td></tr>
                    <tr><th>닉네임</th><td>${member.userName}</td></tr>
                    <tr><th>이름</th><td>${member.name}</td></tr>
                    <tr><th>이메일</th><td>${member.email}</td></tr>
                    <tr><th>전화번호</th><td>${member.phone}</td></tr>
                    <tr><th>주소</th><td>${member.addr}</td></tr>
                    <tr><th>보유 시간</th> <td><i class="fa-regular fa-clock me-2"></i> ${sessionScope.loggedInUser.account}분</td></tr>
                </tbody>
            </table>

            <div class="btn-group-wrap">
                <a href="<c:url value='/mypage/edit?id=${member.member_id}'/>" class="custom-btn btn-edit">정보 수정</a>
                <form action="<c:url value='/mypage/delete'/>" method="post" onsubmit="return confirm('정말 탈퇴하시겠습니까?')" style="display:inline;">
                    <input type="hidden" name="member_id" value="${member.member_id}" />
                    <button type="submit" class="custom-btn btn-delete">회원 탈퇴</button>
                </form>
                <a href="<c:url value='/mypage/changePw' />" class="custom-btn btn-outline">비밀번호 변경</a>
                <a href="<c:url value='/purchase/received' />" class="custom-btn btn-outline">받은 구매 요청</a>
                <a href="<c:url value='/mypage/history' />" class="custom-btn btn-outline">거래 내역</a>
            </div>
        </div>
    </div>
	<c:if test="${not empty myTalentList}">
	    <h5 class="mt-5 mb-3"><i class="fas fa-lightbulb me-2"></i> 내가 등록한 재능</h5>
	    <div class="talent-carousel">
	        <c:forEach var="Talent" items="${myTalentList}">
	            <a href="<c:url value='/talent/view?id=${Talent.talent_id}' />" class="talent-card-link">
	                <div class="talent-card">
	                    <h5>${Talent.title}</h5>
	                    <p><strong>카테고리:</strong> ${Talent.category}</p>
	                    <p><strong>작성자:</strong> ${Talent.username}</p>
	                    <p><strong>판매 시간:</strong> ${Talent.timeSlotDisplay}</p>
	                    <p><strong>등록일:</strong> ${Talent.createdAtDisplay}</p>
	                    <p class="rating">⭐ 평균 별점: ${Talent.averageRating}점 (${Talent.reviewCount}개)</p>
	                </div>
	            </a>
	        </c:forEach>
	    </div>
	</c:if>
    <!-- ✅ 전문가 정보 박스 (시각적으로 구분된 별도 wrapper로 감싸기) -->
    <c:if test="${not empty expertProfile}">
        <div class="expert-wrapper">
            <div class="card">
                <div class="card-header expert">
                    <h4 class="text-white mb-0">등록한 전문가 정보</h4>
                </div>
                <div class="card-body">
                    <table class="table table-bordered align-middle text-center bg-white">
                        <tbody>
                            <tr><th scope="row">경력</th><td>${expertProfile.career}</td></tr>
                            <tr><th scope="row">대학교</th><td>${expertProfile.university}</td></tr>
                            <tr><th scope="row">자격증 및 기타</th><td>${expertProfile.certification}</td></tr>
                        </tbody>
                    </table>

                    <!-- 소개글 -->
                    <div class="mt-4">
                        <h5><i class="fas fa-user-edit me-2"></i> 소개글</h5>
                        <div class="p-3 border rounded bg-light text-start" style="min-height: 80px;">
                            <c:out value="${fn:replace(expertProfile.introduction, '&#10;', '<br/>')}" escapeXml="false" />
                        </div>
                    </div>

                    <!-- 첨부파일 -->
                    <c:if test="${not empty expertProfile.fileNames}">
                        <div class="mt-4">
                            <h5><i class="fas fa-paperclip me-2"></i> 첨부파일</h5>
                            <ul class="list-unstyled ms-2">
                                <c:forEach var="file" items="${expertProfile.fileNames}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/upload/expert/${file}" target="_blank">
                                            <i class="fas fa-file-alt me-1"></i>${file}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <!-- 수정 버튼 -->
                    <div class="btn-group-wrap mt-4">
                        <a href="<c:url value='/mypage/expert/edit' />" class="custom-btn btn-edit">정보 수정</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
