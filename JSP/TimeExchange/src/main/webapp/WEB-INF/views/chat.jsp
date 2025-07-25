<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>실시간 채팅</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/umd/stomp.min.js"></script>
    <style>
        #chatBox {
            width: 500px;
            height: 300px;
            border: 1px solid gray;
            overflow-y: scroll;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
        }
        #inputArea {
            display: flex;
            gap: 10px;
        }
    </style>
</head>
<body>

<h2>🗨️ 채팅방</h2>

<div id="chatBox"></div>

<div id="inputArea">
    <input type="text" id="messageInput" placeholder="메시지를 입력하세요" style="flex: 1;" />
    <button onclick="sendMessage()">전송</button>
</div>

<script>
    let stompClient = null;
    const roomId = "room123"; // 채팅방 ID (테스트용)
    const senderId = "raeun"; // 래은이 아이디 (테스트용)
    const receiverId = "admin"; // 상대방 (예시)

    function connect() {
        const socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("🌐 연결 성공:", frame);

            // 메시지 구독
            stompClient.subscribe("/topic/room/" + roomId, function (message) {
                const msg = JSON.parse(message.body);
                showMessage(msg.senderId + ": " + msg.content);
            });
        });
    }

    function sendMessage() {
        const content = document.getElementById("messageInput").value;
        if (content.trim() === "") return;

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
            roomId: roomId,
            senderId: senderId,
            receiverId: receiverId,
            content: content,
            type: "CHAT",
            createdAt: new Date().toISOString()
        }));

        document.getElementById("messageInput").value = "";
    }

    function showMessage(message) {
        const chatBox = document.getElementById("chatBox");
        const newMessage = document.createElement("div");
        newMessage.textContent = message;
        chatBox.appendChild(newMessage);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    connect();
</script>

</body>
</html>
