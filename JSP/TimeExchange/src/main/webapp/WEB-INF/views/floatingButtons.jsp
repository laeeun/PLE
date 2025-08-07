<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- ✅ Font Awesome CDN -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<c:if test="${not empty sessionScope.loggedInUser}">
    <!-- ✅ 오른쪽 하단 동그란 플로팅 버튼들 -->
    <div class="floating-buttons">
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/chat')}">
            <button class="circle-btn" title="채팅함" onclick="location.href='${pageContext.request.contextPath}/chat/list'">
                <i class="fas fa-comments"></i><br><span class="btn-label">채팅함</span>
                <c:if test="${unreadCount1 > 0}">
                    <span class="badge">${unreadCount1}</span>
                </c:if>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/following')}">
            <button class="circle-btn" title="팔로잉" onclick="location.href='${pageContext.request.contextPath}/follow/following'">
                <i class="fas fa-handshake"></i><br><span class="btn-label">팔로잉</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/followers')}">
            <button class="circle-btn" title="팔로워" onclick="location.href='${pageContext.request.contextPath}/follow/followers'">
                <i class="fas fa-user-friends"></i><br><span class="btn-label">팔로워</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage')}">
            <button class="circle-btn" title="마이페이지" onclick="location.href='<c:url value="/mypage" />'">
                <i class="fas fa-user-circle"></i><br><span class="btn-label">마이페이지</span>
            </button>
        </c:if>
    </div>

    
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
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/withdraw')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/withdraw'">
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
.circle-btn i {
    font-size: 24px;
}
.floating-buttons {
    position: fixed;
    bottom: 20px;
    right: 20px;
    display: flex;
    flex-direction: column;
    gap: 12px;
    z-index: 999;
    animation: fadeInUp 0.8s ease-out;
}
.circle-btn {
    width: 65px;
    height: 65px;
    border-radius: 50%;
    background: linear-gradient(135deg, #d3c6ee, #b5d4f2); /* ⬅ 채도 낮춘 톤 */
    color: white;
    font-size: 24px;
    border: none;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
    transition: transform 0.2s ease, box-shadow 0.3s ease;
    cursor: pointer;
    animation: bounceIn 0.8s ease both;
    position: relative;
    text-align: center;
    line-height: 1.2;
    padding-top: 6px;
}
.circle-btn:hover {
    transform: scale(1.15);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}
.circle-btn .btn-label {
    display: block;
    font-size: 11px;
    margin-top: 2px;
    color: white;
    font-weight: 600;
}
.badge {
    position: absolute;
    top: -5px;
    right: -5px;
    background-color: #e57373; /* 기존보다 부드러운 빨강 */
    color: white;
    font-size: 13px;
    font-weight: bold;
    border-radius: 10px;
    padding: 2px 6px;
}
.floating-list {
  position: fixed;
  top: 150px; /* 상단 여백 */
  left: 100px; 
  width: 220px; /* 원하는 너비 */
  background: var(--surface-alt, #fff);
  border-right: 1px solid var(--border, #e8e8e8);
  padding: 20px 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  z-index: 999;
  height: auto;
  max-height: 300px;
  overflow-y: auto;
  box-shadow: 4px 0 12px rgba(0, 0, 0, 0.05);
  animation: slideInLeft 0.5s ease;
}
.list-btn {
  background: none;
  border: none;
  font-size: 15px;
  color: var(--text-main, #1F2C40);
  text-align: left;
  padding: 10px 12px;
  border-radius: 10px;
  font-weight: 600;
  transition: background-color 0.2s, transform 0.2s;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.list-btn:hover {
  background: linear-gradient(135deg, #ffb066, #ff6b35); /* 밝은 오렌지 → 진한 오렌지 */
  transform: translateX(4px);
  color: white;
}

@keyframes fadeInUp {
    from { opacity: 0; transform: translateY(30px); }
    to { opacity: 1; transform: translateY(0); }
}
@keyframes bounceIn {
    0% { transform: scale(0.3); opacity: 0; }
    50% { transform: scale(1.1); opacity: 1; }
    70% { transform: scale(0.95); }
    100% { transform: scale(1); }
}
@keyframes slideInLeft {
  from {
    transform: translateX(-30px);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}
</style>
