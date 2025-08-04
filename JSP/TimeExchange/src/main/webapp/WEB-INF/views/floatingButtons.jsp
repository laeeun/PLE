<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty sessionScope.loggedInUser}">
    <!-- ✅ 오른쪽 하단 동그란 플로팅 버튼들 -->
    <div class="floating-buttons">
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/chat')}">
            <button class="circle-btn" title="채팅함" onclick="location.href='${pageContext.request.contextPath}/chat/list'">
                💬<br><span class="btn-label">채팅함</span>
                <c:if test="${unreadCount1 > 0}">
                    <span class="badge">${unreadCount1}</span>
                </c:if>
            </button>
        </c:if>
        
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/following')}">
            <button class="circle-btn" title="팔로잉" onclick="location.href='${pageContext.request.contextPath}/follow/following'">
                🤝<br><span class="btn-label">팔로잉</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/follow/followers')}">
            <button class="circle-btn" title="팔로워" onclick="location.href='${pageContext.request.contextPath}/follow/followers'">
                👥<br><span class="btn-label">팔로워</span>
            </button>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage')}">
            <button class="circle-btn" title="마이페이지" onclick="location.href='<c:url value="/mypage" />'">
                🙋<br><span class="btn-label">마이페이지</span>
            </button>
        </c:if>
    </div>

    <!-- ✅ 왼쪽 하단 리스트 버튼들 -->
    <div class="floating-list">
        <c:if test="${not fn:contains(pageContext.request.requestURI, '/todo')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/todo'">📌 TODO 리스트</div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/review/myreviews')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/review/myreviews'">📝 내 리뷰 목록</div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/history')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/history'">📂 거래 내역</div>
        </c:if>

        <c:if test="${not fn:contains(pageContext.request.requestURI, '/purchase/sent')}">
            <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/purchase/sent'">🛍️ 구매 요청</div>
        </c:if>

        <c:if test="${fn:contains(pageContext.request.requestURI, '/mypage')}">
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/edit')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/edit'">⚙️ 회원정보 수정</div>
            </c:if>
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/changePw')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/changePw'">🔑 비밀번호 변경</div>
            </c:if>
            <c:if test="${not fn:contains(pageContext.request.requestURI, '/mypage/withdraw')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/withdraw'">❌ 회원 탈퇴</div>
            </c:if>
			<c:if test="${sessionScope.loggedInUser.expert and not fn:contains(pageContext.request.requestURI, '/mypage/expert')}">
                <div class="list-btn" onclick="location.href='${pageContext.request.contextPath}/mypage/expert/edit'">🧾 전문가 정보 수정</div>
            </c:if>
        </c:if>
    </div>
</c:if>

<!-- ✅ 스타일 통합 -->
<style>
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
    background: linear-gradient(135deg, #e0c3fc, #8ec5fc);
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
.circle-btn:nth-child(1) { animation-delay: 0.1s; }
.circle-btn:nth-child(2) { animation-delay: 0.2s; }
.circle-btn:nth-child(3) { animation-delay: 0.3s; }
.circle-btn:nth-child(4) { animation-delay: 0.4s; }

.btn-label {
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
    background-color: #ff5252;
    color: white;
    font-size: 13px;
    font-weight: bold;
    border-radius: 10px;
    padding: 2px 6px;
}

.floating-list {
    position: fixed;
    bottom: 20px;
    left: 20px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    z-index: 999;
    animation: fadeInUp 0.8s ease-out;
}
.list-btn {
    background: rgba(255, 255, 255, 0.85);
    padding: 10px 16px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    font-size: 14px;
    color: #4b0082;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
    backdrop-filter: blur(4px);
}
.list-btn:hover {
    background: #f3e8ff;
    transform: translateX(4px);
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
</style>
