<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Font Awesome (아이콘) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<c:if test="${not empty sessionScope.loggedInUser}">
    <!-- ✅ 오른쪽 하단 동그란 플로팅 버튼들 -->
    <div class="floating-buttons">
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/chat')}">
            <button class="circle-btn" title="채팅함" onclick="location.href='${pageContext.request.contextPath}/chat/list'">
                <i class="fas fa-comments" aria-hidden="true"></i>
                <span class="btn-label">채팅함</span>
                <c:if test="${unreadCount1 > 0}">
                    <span class="badge" aria-label="안 읽은 채팅">${unreadCount1}</span>
                </c:if>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/following')}">
            <button class="circle-btn" title="팔로잉" onclick="location.href='${pageContext.request.contextPath}/follow/following'">
                <i class="fas fa-handshake" aria-hidden="true"></i>
                <span class="btn-label">팔로잉</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/followers')}">
            <button class="circle-btn" title="팔로워" onclick="location.href='${pageContext.request.contextPath}/follow/followers'">
                <i class="fas fa-user-friends" aria-hidden="true"></i>
                <span class="btn-label">팔로워</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage')}">
            <button class="circle-btn" title="마이페이지" onclick="location.href='<c:url value="/mypage" />'">
                <i class="fas fa-user-circle" aria-hidden="true"></i>
                <span class="btn-label">마이페이지</span>
            </button>
        </c:if>
    </div>

    <!-- ✅ 왼쪽 사이드 리스트 (기존 유지) -->
    <div class="floating-list">
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/todo')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/todo'">
                <i class="fas fa-list-check"></i> TODO 리스트
            </div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/review/myreviews')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/review/myreviews'">
                <i class="fas fa-star-half-alt"></i> 내 리뷰 목록
            </div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/history')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/history'">
                <i class="fas fa-clock-rotate-left"></i> 거래 내역
            </div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/purchase/sent')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/purchase/sent'">
                <i class="fas fa-cart-arrow-down"></i> 구매 요청
            </div>
        </c:if>

        <c:if test="${fn:contains(pageContext.request.requestURI, '/mypage')}">
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/edit')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/edit'">
                    <i class="fas fa-user-gear"></i> 회원정보 수정
                </div>
            </c:if>
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/changePw')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/changePw'">
                    <i class="fas fa-key"></i> 비밀번호 변경
                </div>
            </c:if>
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/deactivated')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/deactivated'">
                    <i class="fas fa-user-slash"></i> 회원 탈퇴
                </div>
            </c:if>
            <c:if test="${sessionScope.loggedInUser.expert and not fn:contains(pageContext.request.requestURI, '/mypage/expert')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/expert/edit'">
                    <i class="fas fa-user-tie"></i> 전문가 정보 수정
                </div>
            </c:if>
        </c:if>
    </div>
</c:if>

<style>
/* ====== Floating Circle Buttons ====== */
.floating-buttons{
  position:fixed; right:20px; bottom:20px;
  display:flex; flex-direction:column; gap:12px; z-index:999;
  animation:fadeInUp .35s ease-out;
}
.circle-btn{
  width:68px; height:68px; border-radius:50%;
  background:var(--surface-alt, #FFFFFF);
  color:var(--accent, #FF6B35);
  border:1px solid var(--border, #E8E8E8);
  box-shadow:0 6px 18px rgba(31,44,64,.06);
  display:flex; flex-direction:column; align-items:center; justify-content:center;
  gap:6px; cursor:pointer;
  transition:transform .18s ease, box-shadow .2s ease, background .2s ease, color .2s ease;
}
.circle-btn i{font-size:22px; line-height:1; color:currentColor;}
.circle-btn .btn-label{display:block; font-size:11px; font-weight:700; letter-spacing:.1px; color:var(--text-main, #1F2C40);}
.circle-btn:hover{transform:translateY(-3px); background:var(--accent, #FF6B35); color:#FFF; box-shadow:0 10px 26px rgba(255,107,53,.25);}
.circle-btn:hover .btn-label{color:#FFF;}
.circle-btn:focus{outline:none;}
.circle-btn:focus-visible{box-shadow:0 0 0 3px rgba(255,107,53,.35), 0 8px 24px rgba(255,107,53,.25);}
.badge{position:absolute; top:-6px; right:-6px; background:var(--accent,#FF6B35); color:#fff; font-size:12px; font-weight:800; line-height:1; border-radius:999px; padding:3px 7px; box-shadow:0 4px 10px rgba(255,107,53,.35);}

/* ====== Left Quick List: 기본(모든 페이지 공통) ====== */
.floating-list{
  position:fixed; top:150px; left:100px; width:220px;
  background:var(--surface-alt, #FFFFFF);
  border-right:1px solid var(--border, #E8E8E8);
  padding:20px 16px; display:flex; flex-direction:column; gap:12px;
  z-index:999; max-height:300px; overflow-y:auto;
  box-shadow:4px 0 12px rgba(0,0,0,.05);
  animation:slideInLeft .3s ease;
}
.list-btn{
  background:none; border:none; cursor:pointer;
  font-size:15px; font-weight:700; color:var(--text-main, #1F2C40);
  text-align:left; display:flex; align-items:center; gap:10px;
  padding:10px 12px; border-radius:12px; transition:background .15s, transform .15s, color .15s;
}
.list-btn:hover{background:linear-gradient(135deg, rgba(255,107,53,.1), rgba(255,107,53,.18)); transform:translateX(4px); color:#1F2C40;}
.list-btn i{min-width:18px;}

@keyframes fadeInUp{from{opacity:0; transform:translateY(18px)} to{opacity:1; transform:translateY(0)}}
@keyframes slideInLeft{from{opacity:0; transform:translateX(-22px)} to{opacity:1; transform:translateX(0)}}

/* 모바일 */
@media (max-width:768px){
  .floating-buttons{right:16px; bottom:16px; gap:10px;}
  .circle-btn{width:64px; height:64px;}
  .floating-list{left:16px; top:120px; width:200px;}
}
</style>

<!-- ✅ 마이페이지 전용: 높이만 ‘세로로 길게’ 조절 (고정 유지) -->
<c:if test="${fn:contains(pageContext.request.requestURI, '/mypage')}">
<style>
  .floating-list{
    position: fixed;          /* 고정 유지 */
    top: 120px; left: 100px;
    width: 240px;             /* 필요시 220~280px로 조절 */
    max-height: calc(100vh - 200px);  /* 화면 하단까지 길게 – 값은 레이아웃에 맞게 180~240px 조정 가능 */
    overflow-y: auto;         /* 정말 넘칠 때만 내부 스크롤 */
    display: flex; flex-direction: column; /* 1열 고정 */
  }
</style>
</c:if>
