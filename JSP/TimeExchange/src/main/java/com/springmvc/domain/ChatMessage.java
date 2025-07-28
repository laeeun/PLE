package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatMessage {
    private String messageId;          // 고유 메시지 ID
    private String roomId;             // 채팅방 ID
    private String senderId;           // 보낸 사람 ID
    private String receiverId;         // 받는 사람 ID (1:1)
    private String senderName;         // 보낸 사람 닉네임
    private String senderProfileImage; // 보낸 사람 프로필 사진
    private String content;            // 메시지 내용
    private String fileUrl;            // 이미지/파일 경로
    private MessageType type;          // 메시지 타입 (CHAT, IMAGE, ENTER, LEAVE 등)
    private LocalDateTime createdAt;   // 보낸 시간
    private boolean isMine;            // 로그인한 사용자 메시지 여부 (프론트에서 활용)
    private boolean read;              // 읽음 여부
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	@Override
	public String toString() {
		return "ChatMessage [messageId=" + messageId + ", roomId=" + roomId + ", senderId=" + senderId + ", receiverId="
				+ receiverId + ", senderName=" + senderName + ", senderProfileImage=" + senderProfileImage
				+ ", content=" + content + ", fileUrl=" + fileUrl + ", type=" + type + ", createdAt=" + createdAt
				+ ", isMine=" + isMine + ", read=" + read + "]";
	}
    
    
 
}
