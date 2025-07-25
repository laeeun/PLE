package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatEntity {
    private Long id;
    private String roomId;
    private String senderId;
    private String receiverId;
    private String content;
    private String type;
    private LocalDateTime createdAt;

    // 기본 생성자
    public ChatEntity() {}

    // 생성자
    public ChatEntity(String roomId, String senderId, String receiverId, String content, String type, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
		return "ChatEntity [id=" + id + ", roomId=" + roomId + ", senderId=" + senderId + ", receiverId=" + receiverId
				+ ", content=" + content + ", type=" + type + ", createdAt=" + createdAt + "]";
	}
    
    
}