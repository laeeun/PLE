<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            background: white;
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 15px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            transition: all 0.2s ease-in-out;
        }
        .chat-card:hover {
            transform: scale(1.02);
        }
        .chat-title {
            font-weight: bold;
            color: #4f46e5;
        }
        .chat-preview {
            color: #555;
        }
        .chat-time {
            font-size: 0.9rem;
            color: gray;
            float: right;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp"/>

<h2>💬 채팅 목록</h2>
<!-- 채팅방 리스트 반복 -->
<c:forEach var="chat" items="${chatList}">
    <a href="<c:url value='/chat/room?roomId=${chat.roomId}'/>" style="text-decoration: none;">
        <div class="chat-card">
            <div class="chat-title">
                ${chat.partnerName}
                <span class="chat-time">${chat.lastMessageTime}</span>
            </div>
            <div class="chat-preview">${chat.lastMessage}</div>
        </div>
    </a>
</c:forEach>

<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
