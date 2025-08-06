<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>TimeFair - 채팅 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            color: var(--text-main);
            min-height: 100vh;
        }
        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
        h2 {
            font-size: 32px;
            font-weight: 900;
            margin: 60px 0 40px;
            color: var(--primary);
            font-family: 'Montserrat', sans-serif;
            text-align: center;
        }
        .chat-card {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: rgba(255,255,255,0.65);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            padding: 18px 24px;
            margin: 0 auto 20px;
            border: 1px solid var(--border);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            max-width: 800px;
            transition: all 0.25s ease;
        }
        .chat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
        }
        .chat-info {
            display: flex;
            align-items: center;
            flex-grow: 1;
        }
        .chat-profile {
            width: 52px;
            height: 52px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 18px;
            border: 1px solid #ccc;
        }
        .chat-text {
            display: flex;
            flex-direction: column;
        }
        .chat-title {
            font-size: 18px;
            font-weight: 700;
            color: var(--primary);
        }
        .chat-preview {
            font-size: 14px;
            color: var(--text-sub);
            display: flex;
            align-items: center;
        }
        .unread-badge {
            background-color: var(--accent);
            color: white;
            border-radius: 12px;
            padding: 3px 8px;
            font-size: 12px;
            font-weight: bold;
            margin-left: 10px;
            min-width: 20px;
            text-align: center;
        }
        .chat-actions {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
            justify-content: space-between;
            gap: 6px;
        }
        .chat-time {
            font-size: 13px;
            color: var(--text-sub);
        }
        .btn-delete {
            background-color: transparent;
            border: none;
            color: #dc3545;
            font-size: 14px;
            cursor: pointer;
        }
        .btn-delete:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp"/>

<h2>💬 채팅 목록</h2>

<c:forEach var="chat" items="${chatList}">
    <div class="chat-card">
        <a href="<c:url value='/chat/room?roomId=${chat.roomId}'/>" style="text-decoration: none; flex-grow: 1;">
            <div class="chat-info">
                <c:set var="isDefaultProfile" value="${empty chat.partnerProfileImage or fn:contains(chat.partnerProfileImage, 'default_profile.png')}" />
                <c:choose>
                    <c:when test="${not isDefaultProfile}">
                        <img src="${pageContext.request.contextPath}${chat.partnerProfileImage}" class="chat-profile" alt="프로필" />
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/images/default-profile.png" class="chat-profile" alt="기본 프로필" />
                    </c:otherwise>
                </c:choose>
                <div class="chat-text">
                    <div class="chat-title">${chat.partnerName}</div>
                    <div class="chat-preview">
                        ${chat.lastMessage}
                        <c:if test="${chat.unreadCount > 0}">
                            <span class="unread-badge">${chat.unreadCount}</span>
                        </c:if>
                    </div>
                </div>
            </div>
        </a>
        <div class="chat-actions">
            <div class="chat-time" data-time="${chat.lastMessageTime}"></div>
            <form method="post" action="<c:url value='/chat/deleteRoom'/>" onsubmit="return confirm('채팅방을 삭제할까요?');">
                <input type="hidden" name="roomId" value="${chat.roomId}" />
                <button type="submit" class="btn-delete">🗑 삭제</button>
            </form>
        </div>
    </div>
</c:forEach>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp"/>
<jsp:include page="/WEB-INF/views/footer.jsp"/>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const timeElements = document.querySelectorAll(".chat-time");
    timeElements.forEach(el => {
        let raw = el.dataset.time;
        if (!raw) return;
        if (raw.includes(" ")) raw = raw.replace(" ", "T");
        const date = new Date(raw);
        if (isNaN(date)) {
            el.textContent = "시간 정보 없음";
            return;
        }
        const formatted = date.toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        });
        el.textContent = formatted;
    });
});
</script>

</body>
</html>
