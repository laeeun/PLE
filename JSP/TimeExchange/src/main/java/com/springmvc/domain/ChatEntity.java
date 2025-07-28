package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatEntity {
    private Long id;
    private Long roomId;
    private String senderId;
    private String receiverId;
    private String senderName;          // 선택: 닉네임 표시
    private String senderProfileImage;  // 선택: 프로필 사진
    private String content;
    private String type;                // CHAT, IMAGE 등
    private String fileUrl;             // 이미지/파일 경로
    private boolean read;               // 읽음 여부
    private LocalDateTime readAt;       // 읽은 시간
    private boolean deleted;            // soft delete 여부
    private LocalDateTime createdAt;

    public ChatEntity() {
        // 기본 생성자
    }
    
    // 생성자
    public ChatEntity(Long roomId, String senderId, String receiverId,
            String content, String type, LocalDateTime createdAt,
            String fileUrl, boolean read, boolean deleted) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.fileUrl = fileUrl;
        this.read = read;
        this.deleted = deleted;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderProfileImage() {
		return senderProfileImage;
	}

	public void setSenderProfileImage(String senderProfileImage) {
		this.senderProfileImage = senderProfileImage;
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public LocalDateTime getReadAt() {
		return readAt;
	}

	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
				+ ", senderName=" + senderName + ", senderProfileImage=" + senderProfileImage + ", content=" + content
				+ ", type=" + type + ", fileUrl=" + fileUrl + ", read=" + read + ", readAt=" + readAt + ", deleted="
				+ deleted + ", createdAt=" + createdAt + "]";
	}

	
}