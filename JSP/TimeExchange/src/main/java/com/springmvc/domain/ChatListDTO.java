package com.springmvc.domain;

import java.time.LocalDateTime;

public class ChatListDTO {
    private String roomId;           // 채팅방 ID
    private String partnerName;      // 상대방 닉네임 (또는 이름)
    private String lastMessage;      // 최근 메시지 내용
    private LocalDateTime lastMessageTime; // 최근 메시지 시간

    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public String getPartnerName() {
        return partnerName;
    }
    
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    
    public String getLastMessage() {
        return lastMessage;
    }
    
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    
    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }
    
    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

	@Override
	public String toString() {
		return "ChatListDTO [roomId=" + roomId + ", partnerName=" + partnerName + ", lastMessage=" + lastMessage
				+ ", lastMessageTime=" + lastMessageTime + "]";
	}
    
    
}
