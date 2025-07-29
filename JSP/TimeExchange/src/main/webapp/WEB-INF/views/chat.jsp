<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>실시간 채팅</title>

    <!-- 감성 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <!-- JS 라이브러리 -->
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.6.1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #fce7f3, #e0f2fe);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .chat-container {
            background: rgba(255, 255, 255, 0.35);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
            width: 500px;
            max-width: 90vw;
            padding: 25px;
        }

        .chat-title {
            font-size: 1.6rem;
            font-weight: bold;
            text-align: center;
            color: #d63384;
            margin-bottom: 20px;
        }

        #chatBox {
            width: 100%;
            height: 300px;
            border-radius: 12px;
            padding: 15px;
            overflow-y: auto;
            background-color: rgba(255, 255, 255, 0.85);
            border: 1px solid #ffcce0;
            margin-bottom: 15px;
            display: flex;
            flex-direction: column;
        }

        .message-wrapper {
            display: flex;
            flex-direction: column;
            margin-bottom: 10px;
        }

        .message {
            padding: 10px 14px;
            border-radius: 12px;
            max-width: 75%;
            word-break: break-word;
            font-size: 14px;
        }

        .my-message {
            background-color: #ffd6ec;
            align-self: flex-end;
            text-align: right;
            margin-left: auto;
        }

        .other-message {
            background-color: #ffffff;
            align-self: flex-start;
            text-align: left;
            margin-right: auto;
            border: 1px solid #eee;
        }

        .sender-label {
            font-size: 12px;
            color: #888;
            margin-bottom: 3px;
            padding: 0 4px;
        }

        #inputArea {
            display: flex;
            gap: 10px;
        }

        #messageInput {
            flex: 1;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 10px;
            font-size: 14px;
            outline: none;
        }

        #messageInput:focus {
            border-color: #ff99cc;
        }

        button {
            background-color: #ff99cc;
            border: none;
            padding: 10px 18px;
            border-radius: 10px;
            font-weight: bold;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #ff66a3;
        }
    </style>
</head>
<body>

<script>
    const contextPath = "<%= request.getContextPath() %>";
</script>

<div class="chat-container">
    <div class="chat-title">💬 채팅방</div>

    <div id="chatBox"></div>

    <div id="inputArea">
        <input type="text" id="messageInput" placeholder="메시지를 입력하세요" />
        <button onclick="sendMessage()">전송</button>
    </div>
</div>

<script>
    let stompClient = null;

    const roomId = "${roomId}";
    const senderId = "${senderId}";
    const receiverId = "${receiverId}";

    function connect() {
        const socket = new SockJS(contextPath + "/ws");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("🌐 연결 성공:", frame);

            stompClient.subscribe("/topic/room/" + roomId, function (message) {
                const msg = JSON.parse(message.body);
                showMessage(msg);
            });

            loadChatHistory();
        }, function (error) {
            console.error("❌ 연결 실패:", error);
        });
    }

    function loadChatHistory() {
        fetch(contextPath + "/chat/messages?roomId=" + roomId)
            .then(response => response.json())
            .then(data => {
                data.forEach(msg => {
                    showMessage(msg);
                });
            })
            .catch(error => {
                console.error("❌ 채팅 기록 불러오기 실패:", error);
            });
    }

    function sendMessage() {
        const content = document.getElementById("messageInput").value.trim();
        if (content === "") return;

        if (!stompClient || !stompClient.connected) {
            alert("⚠️ 웹소켓 연결되지 않았어요!");
            return;
        }

        const msg = {
            roomId: roomId,
            senderId: senderId,
            receiverId: receiverId,
            content: content,
            type: "CHAT",
            createdAt: new Date().toISOString()
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(msg));


        document.getElementById("messageInput").value = "";
    }

    function showMessage(msg) {
        const chatBox = document.getElementById("chatBox");

        const wrapper = document.createElement("div");
        wrapper.classList.add("message-wrapper");

        const label = document.createElement("div");
        label.classList.add("sender-label");
        label.textContent = (msg.senderId === senderId) ? "나" : msg.senderId;

        const messageDiv = document.createElement("div");
        messageDiv.classList.add("message");
        messageDiv.classList.add(msg.senderId === senderId ? "my-message" : "other-message");
        messageDiv.textContent = msg.content;

        wrapper.appendChild(label);
        wrapper.appendChild(messageDiv);

        chatBox.appendChild(wrapper);
        chatBox.scrollTop = chatBox.scrollHeight;
        
    }

    document.getElementById("messageInput").addEventListener("keydown", function (e) {
        if (e.key === "Enter") sendMessage();
    });

    connect();
</script>

</body>
</html>
