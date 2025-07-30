<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>채팅 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #e0e7ff, #f3e8ff);
            min-height: 100vh;
            padding: 30px;
        }

        .chat-card {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: white;
            border-radius: 15px;
            padding: 15px 20px;
            margin-bottom: 15px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            transition: all 0.2s ease-in-out;
        }

        .chat-card:hover {
            transform: scale(1.02);
        }

        .chat-info {
            display: flex;
            align-items: center;
            flex-grow: 1;
        }

        .chat-profile {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 15px;
            border: 1px solid #ccc;
        }

        .chat-text {
            display: flex;
            flex-direction: column;
        }

        .chat-title {
            font-weight: bold;
            color: #4f46e5;
            font-size: 1.05rem;
        }

        .chat-preview {
            color: #555;
            font-size: 0.92rem;
            display: flex;
            align-items: center;
        }

        .chat-time {
            font-size: 0.85rem;
            color: gray;
        }

        .chat-actions {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
        }

        .delete-form {
            margin-top: 5px;
        }

        .btn-delete {
            background-color: transparent;
            border: none;
            color: #dc3545;
            font-size: 0.9rem;
            cursor: pointer;
        }

        .btn-delete:hover {
            text-decoration: underline;
        }

        .unread-badge {
            background-color: #ef4444;
            color: white;
            border-radius: 12px;
            padding: 2px 6px;
            font-size: 12px;
            font-weight: bold;
            margin-left: 8px;
            display: inline-block;
            min-width: 20px;
            text-align: center;
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
            <div class="chat-time">${chat.lastMessageTime}</div>
            <form class="delete-form" method="post" action="<c:url value='/chat/deleteRoom'/>"
                  onsubmit="return confirm('채팅방을 삭제할까요?');">
                <input type="hidden" name="roomId" value="${chat.roomId}" />
                <button type="submit" class="btn-delete">🗑 삭제</button>
            </form>
        </div>
    </div>
</c:forEach>

<jsp:include page="/WEB-INF/views/footer.jsp"/>

</body>
</html>
