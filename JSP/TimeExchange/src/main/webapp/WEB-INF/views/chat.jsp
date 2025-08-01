<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .btn-outline-secondary {
            background: none;
            border: 1px solid #ff99cc;
            color: #d63384;
            padding: 8px 16px;
            border-radius: 10px;
            font-weight: bold;
            text-decoration: none;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .btn-outline-secondary:hover {
            background-color: #ffebf5;
            color: #d63384;
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

    <!-- ✅ 버튼을 내부로 이동 -->
    <div style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/chat/list" class="btn-outline-secondary">
            ← 채팅 목록으로 돌아가기
        </a>
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
        });
    }

    function loadChatHistory() {
        fetch(contextPath + "/chat/messages?roomId=" + roomId)
            .then(response => response.json())
            .then(data => {
            	console.log(data);
                data.forEach(showMessage);
            });
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

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(msg));
        document.getElementById("messageInput").value = "";
    }

    function showMessage(msg) {
        const chatBox = document.getElementById("chatBox");
        const wrapper = document.createElement("div");
        wrapper.classList.add("message-wrapper");

        const isMine = (msg.senderId === senderId);
        
        console.log(msg.createdAt);
        const timeString = (() => {
            const arr = msg.createdAt;

            if (!Array.isArray(arr) || arr.length < 3) return "시간 정보 없음";

            const year = Number(arr[0]);
            const month = Number(arr[1]) - 1;
            const day = Number(arr[2]);
            const hour = Number(arr[3] ?? 0);
            const minute = Number(arr[4] ?? 0);
            const second = Number(arr[5] ?? 0);

            const date = new Date(year, month, day, hour, minute, second);

            if (isNaN(date.getTime())) {
                console.warn("❌ 유효하지 않은 날짜:", arr, date);
                return "시간 정보 없음";
            }

            const hours = date.getHours();
            const minutes = String(date.getMinutes()).padStart(2, "0");
            const period = hours >= 12 ? "오후" : "오전";
            const hour12 = hours % 12 === 0 ? 12 : hours % 12;

            return `\${period} \${hour12}:\${minutes}`;
        })();
       
		
        const readIcon = msg.read === true ? "✔️" : "⭕";

        if (!isMine) {
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
            timeSpan.textContent = timeString; // ✅ 이제 이 timeString은 "오전/오후 시간:분" 형태입니다.

            msgBlock.appendChild(label);
            msgBlock.appendChild(messageDiv);
            msgBlock.appendChild(timeSpan);

            row.appendChild(profileImg);
            row.appendChild(msgBlock);
            wrapper.appendChild(row);
        } else {
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
            // readIcon은 읽음 여부 (✔️, ⭕) 이므로 그대로 사용
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
            timeSpan.textContent = timeString; // ✅ 이제 이 timeString은 "오전/오후 시간:분" 형태입니다.

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

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />


</body>
</html>
