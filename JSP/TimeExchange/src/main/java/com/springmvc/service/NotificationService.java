package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.NotificationDTO;

public interface NotificationService {
	
    //알림 생성시 필요한 dto 생성
    void createSimpleNotification(String sender, String receiver, String type, String content, Long targetId, String targetType);
    
    // 알림 목록 조회 (최신순)
    List<NotificationDTO> getNotificationsByTypeAndPage(String receiverUsername, String type, int offset, int limit);
    
    int countNotifications(String receiverUsername, String type);
    
    // 단일 알림 조회
    NotificationDTO getDetailNotification(long notificationId);

    // 단일 알림 읽음 처리
    void markAsRead(long notificationId);

    // 전체 알림 읽음 처리 (선택)
    void markAllAsRead(String receiverUsername);

    // 안 읽은 알림 개수 조회
    int countUnread(String receiverUsername);

    // 단일 알림 읽음 여부 확인 (선택)
    boolean isRead(long notificationId);

    // 알림 삭제
    void deleteNotification(long notificationId);
    
   
}
