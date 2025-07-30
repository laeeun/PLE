<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- floatingButtons.jsp - 고정 플로팅 버튼 (애니메이션 포함) -->

<div class="floating-buttons">
    <!-- ✅ 채팅함 -->
    <button class="circle-btn" title="채팅함"
            onclick="location.href='${pageContext.request.contextPath}/chat/list'">💬</button>

    <!-- ✅ 내 구매 신청 -->
    <button class="circle-btn" title="내 구매 신청"
            onclick="location.href='${pageContext.request.contextPath}/purchase/sent'">🛍️</button>

    <!-- ✅ 내 리뷰 목록 -->
    <button class="circle-btn" title="내 리뷰 목록"
            onclick="location.href='${pageContext.request.contextPath}/review/myreviews'">📝</button>

    <!-- ✅ 팔로잉 -->
    <button class="circle-btn" title="팔로잉"
            onclick="location.href='${pageContext.request.contextPath}/follow/following'">🤝</button>

    <!-- ✅ 내 팔로워 -->
    <button class="circle-btn" title="내 팔로워"
            onclick="location.href='${pageContext.request.contextPath}/follow/followers'">👥</button>
</div>

<style>
/* ✅ 컨테이너 고정 위치 */
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

/* ✅ 둥글고 입체감 있는 버튼 */
.circle-btn {
    width: 55px;
    height: 55px;
    border-radius: 50%;
    background: linear-gradient(135deg, #e0c3fc, #8ec5fc);
    color: white;
    font-size: 24px;
    border: none;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
    transition: transform 0.2s ease, box-shadow 0.3s ease;
    cursor: pointer;
    animation: bounceIn 0.8s ease both;
}

/* ✅ 순차적으로 등장하는 느낌 (각 버튼 딜레이 줌) */
.circle-btn:nth-child(1) { animation-delay: 0.1s; }
.circle-btn:nth-child(2) { animation-delay: 0.2s; }
.circle-btn:nth-child(3) { animation-delay: 0.3s; }
.circle-btn:nth-child(4) { animation-delay: 0.4s; }

/* ✅ hover 시 약간 튀는 느낌 */
.circle-btn:hover {
    transform: scale(1.15);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

/* ✅ 애니메이션 정의 */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes bounceIn {
    0% {
        transform: scale(0.3);
        opacity: 0;
    }
    50% {
        transform: scale(1.1);
        opacity: 1;
    }
    70% {
        transform: scale(0.95);
    }
    100% {
        transform: scale(1);
    }
}
</style>
