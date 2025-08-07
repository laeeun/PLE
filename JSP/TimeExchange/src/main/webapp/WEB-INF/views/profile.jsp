<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${member.userName}님의 마이페이지</title>

    <!-- Pretendard + Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" />

    <style>
        :root{
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-light: #FFEDE3;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
            --border: #E8E8E8;
        }

        body{
            font-family:'Pretendard',sans-serif;
            background:var(--surface);
            color:var(--text-main);
        }

        /* ===== Card ===== */
        .card{
            background:var(--surface-alt);
            border:1px solid var(--border);
            border-radius:16px;
            box-shadow:0 6px 20px rgba(0,0,0,.03);
            margin-bottom:2rem;
        }
        .card-header{
            background:var(--accent);
            color:#fff;
            border-top-left-radius:16px;
            border-top-right-radius:16px;
        }

        /* ===== Profile image ===== */
        .profile-img-box img{
            width:100px;
            height:100px;
            object-fit:cover;
            border-radius:50%;
        }

        /* ===== Links & text ===== */
        .talent-title{
            font-weight:600;
            color:var(--primary);
            text-decoration:none;
        }
        .talent-title:hover{color:var(--accent);text-decoration:underline;}
        .talent-category{font-size:.9rem;color:var(--text-sub);margin-left:6px;}
        .talent-date{font-size:.85rem;color:#aaa;margin-left:10px;}
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp"/>

<div class="container mt-5">
    <c:set var="loginId"  value="${sessionScope.loggedInUser.member_id}"/>
    <c:set var="otherId"  value="${member.member_id}"/>
    <c:set var="roomId"   value="${loginId}_${otherId}"/>

    <!-- ⭐ 프로필 카드 -->
    <div class="card">
        <div class="card-header d-flex align-items-center justify-content-between flex-wrap gap-3">
            <!-- 프로필 + 닉네임 -->
            <div class="d-flex align-items-center gap-3 flex-wrap">
                <div class="profile-img-box">
                    <c:choose>
                        <c:when test="${not empty member.profileImage && member.profileImage ne 'default-profile.png'}">
                            <img src="<c:url value='/upload/profile/${member.profileImage}'/>" alt="프로필 이미지"/>
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/resources/images/default-profile.png'/>" alt="기본 프로필"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <h4 class="mb-0 fw-bold text-white">${member.userName}님의 프로필</h4>
            </div>

            <!-- 팔로우 & 채팅 버튼 -->
            <c:if test="${not empty loggedInUser and loggedInUser.member_id ne member.member_id}">
                <div class="d-flex gap-2">
                    <button id="followBtn"
                            class="btn ${isFollowing ? 'btn-outline-light' : 'btn-outline-dark'}"
                            data-id="${member.member_id}">
                        ${isFollowing ? '언팔로우 💔' : '팔로우 💗'}
                    </button>
                    <a href="<c:url value='/chat/room?roomId=${roomId}'/>" class="btn btn-light">
                        💬 채팅 보내기
                    </a>
                </div>
            </c:if>
        </div>

        <div class="card-body">
            <p><strong>ID:</strong> ${member.member_id}</p>
            <p><strong>닉네임:</strong> ${member.userName}</p>
            <p><strong>이름:</strong> ${member.name}</p>
            <p><strong>이메일:</strong> ${member.email}</p>
        </div>
    </div>

    <!-- 🧑‍🏫 전문가 정보 -->
    <c:if test="${not empty expertProfile}">
        <div class="card">
            <div class="card-header"><h5 class="mb-0">🧑‍🏫 전문가 정보</h5></div>
            <div class="card-body">
                <p><strong>경력:</strong> ${expertProfile.career}</p>
                <p><strong>출신 대학:</strong> ${expertProfile.university}</p>
                <p><strong>자격증:</strong> ${expertProfile.certification}</p>

                <c:if test="${not empty expertProfile.fileNames}">
                    <p class="mb-1"><strong>첨부 파일:</strong></p>
                    <ul class="mb-0">
                        <c:forEach var="file" items="${expertProfile.fileNames}">
                            <li>
                                <a href="<c:url value='/upload/expert/${file}'/>" target="_blank">${file}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </c:if>

    <!-- 🎨 재능 목록 -->
    <div class="card">
        <div class="card-header"><h5 class="mb-0">재능 목록</h5></div>
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty talentlist}">
                    <ul class="list-unstyled mb-0">
                        <c:forEach var="talent" items="${talentlist}">
                            <li class="mb-2">
                                <a class="talent-title" href="<c:url value='/talent/view?id=${talent.talent_id}'/>">
                                    ${talent.title}
                                </a>
                                <span class="talent-category">[${talent.category}]</span>
                                <span class="talent-date">| 등록일: ${talent.formattedCreatedAt}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p class="text-muted mb-0">등록된 재능이 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp"/>

<!-- ===== JS ===== -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    const API_URL = { toggleFollow : '<c:url value="/follow/toggle"/>' };

    $(function(){
        $('#followBtn').on('click',function(){
            const $btn = $(this);
            $.post(API_URL.toggleFollow,{ followingId : $btn.data('id')},res=>{
                if(res==='followed'){
                    $btn.text('언팔로우 💔')
                        .removeClass('btn-outline-dark').addClass('btn-outline-light');
                }else if(res==='unfollowed'){
                    $btn.text('팔로우 💗')
                        .removeClass('btn-outline-light').addClass('btn-outline-dark');
                }
            });
        });
    });
</script>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp"/>
</body>
</html>
