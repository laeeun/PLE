package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatListDTO {
    private Long roomId;
    private String partnerId;
    private String partnerName;
    private String partnerProfileImage;
    private String lastMessage;
    private MessageType lastMessageType;
    private LocalDateTime lastMessageTime;
    private int unreadCount;
    private boolean isOnline;
    private boolean isMine;
    
    
    
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerProfileImage() {
		return partnerProfileImage;
	}
	public void setPartnerProfileImage(String partnerProfileImage) {
		this.partnerProfileImage = partnerProfileImage;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public MessageType getLastMessageType() {
		return lastMessageType;
	}
	public void setLastMessageType(MessageType lastMessageType) {
		this.lastMessageType = lastMessageType;
	}
	public LocalDateTime getLastMessageTime() {
		return lastMessageTime;
	}
	public void setLastMessageTime(LocalDateTime lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}
	public int getUnreadCount() {
		return unreadCount;
	}
	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	@Override
	public String toString() {
		return "ChatListDTO [roomId=" + roomId + ", partnerId=" + partnerId + ", partnerName=" + partnerName
				+ ", partnerProfileImage=" + partnerProfileImage + ", lastMessage=" + lastMessage + ", lastMessageType="
				+ lastMessageType + ", lastMessageTime=" + lastMessageTime + ", unreadCount=" + unreadCount
				+ ", isOnline=" + isOnline + ", isMine=" + isMine + "]";
	}

  
    
}
