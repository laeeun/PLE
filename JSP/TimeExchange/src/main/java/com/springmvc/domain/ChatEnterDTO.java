package com.springmvc.domain;


public class ChatEnterDTO {
    private String roomId;   // 입장한 채팅방 ID
    private String userId;   // 입장한 사용자 ID

    
    public ChatEnterDTO() {}

    public ChatEnterDTO(String roomId, String userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

