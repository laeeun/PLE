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

        .card {
            border: none;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            animation: fadeInUp 0.6s ease both;
        }

        @keyframes fadeInUp {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        .card-header {
            background: linear-gradient(to right, #d946ef, #a855f7);
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
            padding: 20px;
        }

        .card-header h4 {
            color: #fff;
            font-weight: bold;
            margin: 0;
        }

        .card-body p {
            font-size: 1rem;
            color: #4c1d95;
            margin: 0.5rem 0;
        }

        .card-body strong {
            color: #9333ea;
        }

        .custom-btn {
            padding: 12px 20px;
            font-size: 15px;
            font-weight: 600;
            border-radius: 10px;
            transition: all 0.3s ease;
            border: none;
            color: white;
            text-decoration: none;
            display: inline-block;
        }

        .btn-edit {
            background: linear-gradient(to right, #38bdf8, #6366f1);
            box-shadow: 0 4px 14px rgba(99, 102, 241, 0.4);
        }

        .btn-delete {
            background: linear-gradient(to right, #fb7185, #f43f5e);
            box-shadow: 0 4px 14px rgba(251, 113, 133, 0.4);
        }

        .btn-outline {
            background: white;
            color: #7e22ce;
            border: 2px solid #d8b4fe;
            box-shadow: 0 4px 10px rgba(168, 85, 247, 0.2);
        }

        .custom-btn:hover {
            transform: translateY(-2px);
            opacity: 0.95;
        }

        .btn-group-wrap {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-top: 30px;
        }
        
        .profile-image {
		    width: 120px;
		    height: 120px;
		    object-fit: cover;
		    border-radius: 50%;
		    border: 4px solid white;
		    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
		    background-color: #f9fafb;
		    margin: 0 auto 20px;
		    display: block;
		}
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5 mb-5">
    <div class="card">
        <div class="card-header">
            <h4>마이페이지</h4>
        </div>
        <div class="card-body">
        
        	<c:choose>
			  <c:when test="${member.profileImage ne null and member.profileImage ne 'default_profile.png' and fn:length(member.profileImage) > 0}">
			      <img src="${pageContext.request.contextPath}/upload/profile/${member.profileImage}" alt="프로필 이미지" class="profile-image" />
			  </c:when>
			  <c:otherwise>
			      <img src="<c:url value='/resources/images/default-profile.png' />" alt="기본 이미지" class="profile-image" />
			  </c:otherwise>
			</c:choose>


        	
            <p><strong>ID:</strong> ${member.member_id}</p>
            <p><strong>닉네임:</strong> ${member.userName}</p>
            <p><strong>이름:</strong> ${member.name}</p>
            <p><strong>이메일:</strong> ${member.email}</p>
            <p><strong>전화번호:</strong> ${member.phone}</p>
            <p><strong>주소:</strong> ${member.addr}</p>
            
            <!-- 🕒 보유 시간 (잔액) -->
    		<p><strong>⏰ 보유 시간:</strong> ${sessionScope.loggedInUser.account} 시간</p>
            

            <div class="btn-group-wrap">
				<a href="<c:url value='/mypage/edit?id=${member.member_id}'/>" class="custom-btn btn-edit">정보 수정</a>
			
			    <form action="<c:url value='/mypage/delete'/>" method="post"
			          onsubmit="return confirm('정말 탈퇴하시겠습니까?')"
			          style="display:inline;">
			        <input type="hidden" name="member_id" value="${member.member_id}" />
			        <button type="submit" class="custom-btn btn-delete">회원 탈퇴</button>
			    </form>
			
			    <a href="<c:url value='/mypage/changePw' />" class="custom-btn btn-outline">비밀번호 변경</a>
			
			    <a href="<c:url value='/purchase/received' />" class="custom-btn btn-outline">받은 구매 요청</a>
			    <a href="<c:url value='/mypage/history' />" class="custom-btn btn-outline">거래 내역</a>
			    <a href="<c:url value='/purchase/sent' />" class="custom-btn btn-outline">내 구매 신청</a>
			    <a href="<c:url value='/review/myreviews' />" class="custom-btn btn-outline">내 리뷰 목록</a>
			    <a href="<c:url value='/chat/list' />" class="custom-btn btn-outline">💬 채팅함</a>
			    <a href="<c:url value='/follow/followers' />" class="custom-btn btn-outline">내 팔로워</a>
             	<a href="<c:url value='/follow/following' />" class="custom-btn btn-outline">팔로잉</a>
            </div>
        </div>
        <c:if test="${not empty expertProfile}">
	    <div class="card mt-4">
	        <div class="card-header" style="background: linear-gradient(to right, #9333ea, #8b5cf6);">
	            <h4>📄 등록한 전문가 정보</h4>
	        </div>
	        <div class="card-body">
	            <p><strong>경력:</strong> ${expertProfile.career}</p>
	            <p><strong>대학교:</strong> ${expertProfile.university}</p>
	            <p><strong>자격증 및 기타:</strong> ${expertProfile.certification}</p>
	            <p><strong>소개글:</strong><br/>
				    <c:out value="${fn:replace(expertProfile.introduction, '&#10;', '<br/>')}" escapeXml="false" />
				</p>
	            
	
	            <c:if test="${not empty expertProfile.fileNames}">
	                <p><strong>첨부파일:</strong></p>
	                <ul>
	                    <c:forEach var="file" items="${expertProfile.fileNames}">
	                        <li>
	                            <a href="${pageContext.request.contextPath}/upload/expert/${file}" target="_blank">${file}</a>
	                        </li>
	                    </c:forEach>
	                </ul>
	            </c:if>
	
	            <div class="btn-group-wrap">
	                <a href="<c:url value='/mypage/expert/edit' />" class="custom-btn btn-edit">정보 수정</a>
	            </div>
	        </div>
	    </div>
	</c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
