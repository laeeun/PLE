package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatMessage {
    private String roomId;       // 채팅방 고유 ID
    private String senderId;     // 보낸 사람 ID
    private String receiverId;   // 받는 사람 ID (1:1일 경우)
    private String content;      // 메시지 본문
    private MessageType type;    // 메시지 타입 (CHAT, ENTER, LEAVE 등)
    private LocalDateTime createdAt; //메시지 보낸 시간
    
    
    
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ChatMessage [roomId=" + roomId + ", senderId=" + senderId + ", receiverId=" + receiverId + ", content="
				+ content + ", type=" + type + ", createdAt=" + createdAt + "]";
	}
    
    
}
