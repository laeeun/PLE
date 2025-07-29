package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.NotificationDTO;
import com.springmvc.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    
   
    @Override
    public void createSimpleNotification(String sender, String receiver, String type, String content, Long targetId, String targetType) {
        NotificationDTO noti = new NotificationDTO();
        noti.setSender_username(sender);
        noti.setReceiver_username(receiver);
        noti.setType(type);
        noti.setContent(content);
        if (targetId != null) {
            noti.setTargetId(targetId);
        }
        if (targetType != null) {
            noti.setTarget_type(targetType);
        }
        noti.setRead(false);  // 기본적으로 읽지 않음
        noti.setCreated_at(java.time.LocalDateTime.now());

        notificationRepository.createNotification(noti);
    }

	@Override
    public List<NotificationDTO> getNotificationsByTypeAndPage(String receiverUsername, String type, int offset, int limit) {
        return notificationRepository.getNotificationsByTypeAndPage(receiverUsername, type, offset, limit);
    }

    @Override
    public int countNotifications(String receiverUsername, String type) {
        return notificationRepository.countNotifications(receiverUsername, type);
    }

    @Override
    public NotificationDTO getDetailNotification(long notificationId) {
        return notificationRepository.getDetailNotification(notificationId);
    }

    @Override
    public void markAsRead(long notificationId) {
        notificationRepository.markAsRead(notificationId);
    }

    @Override
    public void markAllAsRead(String receiverUsername) {
        notificationRepository.markAllAsRead(receiverUsername);
    }

    @Override
    public int countUnread(String receiverUsername) {
        return notificationRepository.countUnread(receiverUsername);
    }

    @Override
    public boolean isRead(long notificationId) {
        return notificationRepository.isRead(notificationId);
    }

    @Override
    public void deleteNotification(long notificationId) {
        notificationRepository.deleteNotification(notificationId);
    }
}
