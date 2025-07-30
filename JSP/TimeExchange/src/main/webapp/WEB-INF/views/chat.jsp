<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>실시간 채팅</title>

    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.6.1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

    <style>
        * { box-sizing: border-box; }
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
            max-width: 100%;
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
        .profile-img {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            margin-right: 10px;
            object-fit: cover;
            border: 1px solid #ccc;
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
        .read-meta {
            font-size: 12px;
            color: #888;
            white-space: nowrap;
            align-self: center;
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
            console.log("✅ 웹소켓 연결됨:", frame);

            stompClient.subscribe("/topic/room/" + roomId, function (message) {
                const msg = JSON.parse(message.body);
                showMessage(msg);
            });

            stompClient.subscribe("/topic/read/" + senderId, function (message) {
                const readRoomId = message.body;
                if (readRoomId === roomId) {
                    document.querySelectorAll(".read-meta").forEach(el => {
                        el.textContent = el.textContent.replace("⭕", "✔️");
                    });
                }
            });

            
            stompClient.send("/app/chat.enter", {}, JSON.stringify({
                roomId: roomId,
                userId: senderId
            }));

            loadChatHistory();
        }, function (error) {
            console.error("❌ 웹소켓 연결 실패:", error);
        });
    }

    function loadChatHistory() {
        fetch(contextPath + "/chat/messages?roomId=" + roomId)
            .then(response => response.json())
            .then(data => {
                data.forEach(showMessage);
            })
            .catch(error => console.error("❌ 기록 불러오기 실패:", error));
    }

    function sendMessage() {
        const content = document.getElementById("messageInput").value.trim();
        if (!content) return;

        if (!stompClient || !stompClient.connected) {
            alert("⚠️ 연결되지 않았습니다.");
            return;
        }

        const msg = {
            roomId: roomId,
            senderId: senderId,
            receiverId: receiverId,
            content: content,
            type: "CHAT"
        };
        
        console.log("보낼 메시지 객체:", msg); 

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(msg));
        document.getElementById("messageInput").value = "";
    }

    function showMessage(msg) {
        const chatBox = document.getElementById("chatBox");
        const wrapper = document.createElement("div");
        wrapper.classList.add("message-wrapper");

        const isMine = (msg.senderId === senderId);

        // ✅ 날짜 처리 (문자열 or 배열 모두 대응)
        const rawTime = msg.createdAt;
        let timeString = "";

        if (Array.isArray(rawTime)) {
            // 예: [2025, 7, 30, 17, 54, 54, 123456789]
            const hour = rawTime[3];
            const minute = rawTime[4];
            const period = hour >= 12 ? "오후" : "오전";
            const formattedHour = hour % 12 === 0 ? 12 : hour % 12;
            timeString = `\${period} \${formattedHour}:\${String(minute || 0).padStart(2, "0")}`;
        } else {
            let time = rawTime ? new Date(rawTime) : null;
            if (time && !isNaN(time.getTime())) {
                const hour = time.getHours();
                const minute = time.getMinutes().toString().padStart(2, "0");
                const period = hour >= 12 ? "오후" : "오전";
                const formattedHour = hour % 12 === 0 ? 12 : hour % 12;
                timeString = `${period} ${formattedHour}:${minute}`;
            } else {
                timeString = "시간정보없음";
            }
        }

        const readIcon = msg.read === true ? "✔️" : "⭕";

        if (!isMine) {
            // 받은 메시지
            const row = document.createElement("div");
            row.style.display = "flex";
            row.style.alignItems = "flex-start";
            row.style.marginBottom = "10px";

            const profileImg = document.createElement("img");
            profileImg.src = contextPath + (msg.senderProfileImage || "/resources/images/default-profile.png");
            profileImg.classList.add("profile-img");
            profileImg.onerror = function () {
                this.onerror = null;
                this.src = contextPath + "/resources/images/default-profile.png";
            };

            const msgBlock = document.createElement("div");
            msgBlock.style.display = "flex";
            msgBlock.style.flexDirection = "column";
            msgBlock.style.alignItems = "flex-start";

            const label = document.createElement("div");
            label.classList.add("sender-label");
            label.textContent = msg.senderName || msg.senderId;

            const messageDiv = document.createElement("div");
            messageDiv.classList.add("message", "other-message");
            messageDiv.textContent = msg.content;

            const timeSpan = document.createElement("div");
            timeSpan.classList.add("read-status");
            timeSpan.style.fontSize = "12px";
            timeSpan.style.color = "#999";
            timeSpan.style.marginTop = "2px";
            timeSpan.textContent = timeString;

            msgBlock.appendChild(label);
            msgBlock.appendChild(messageDiv);
            msgBlock.appendChild(timeSpan);

            row.appendChild(profileImg);
            row.appendChild(msgBlock);
            wrapper.appendChild(row);
        } else {
            // 보낸 메시지
            const row = document.createElement("div");
            row.style.display = "flex";
            row.style.justifyContent = "flex-end";
            row.style.marginBottom = "10px";

            const msgBlock = document.createElement("div");
            msgBlock.style.display = "flex";
            msgBlock.style.flexDirection = "column";
            msgBlock.style.alignItems = "flex-end";

            const innerWrapper = document.createElement("div");
            innerWrapper.style.display = "flex";
            innerWrapper.style.alignItems = "flex-end";
            innerWrapper.style.gap = "4px";

            const readSpan = document.createElement("div");
            readSpan.classList.add("read-status");
            readSpan.style.fontSize = "12px";
            readSpan.style.color = "#999";
            readSpan.textContent = readIcon;

            const messageDiv = document.createElement("div");
            messageDiv.classList.add("message", "my-message");
            messageDiv.textContent = msg.content;

            innerWrapper.appendChild(readSpan);
            innerWrapper.appendChild(messageDiv);

            const timeSpan = document.createElement("div");
            timeSpan.style.fontSize = "11px";
            timeSpan.style.color = "#999";
            timeSpan.style.marginTop = "2px";
            timeSpan.textContent = timeString;

            msgBlock.appendChild(innerWrapper);
            msgBlock.appendChild(timeSpan);

            row.appendChild(msgBlock);
            wrapper.appendChild(row);
        }

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
