package com.springmvc.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationDTO {
	private long notificationId;
	private String receiver_username;
	private String sender_username;
	private String type;
	private String content;
	private long targetId;
	private String target_type;
	private boolean read;
	private LocalDateTime created_at = LocalDateTime.now();
	public NotificationDTO(long notificationId, String receiver_username, String sender_username, String type,
			String content, long targetId, String target_type, boolean read, LocalDateTime created_at) {
		super();
		this.notificationId = notificationId;
		this.receiver_username = receiver_username;
		this.sender_username = sender_username;
		this.type = type;
		this.content = content;
		this.targetId = targetId;
		this.target_type = target_type;
		this.read = read;
		this.created_at = created_at;
	}
	public NotificationDTO() {}
	
	public String getFormattedCreatedAt() {
	    if (created_at == null) return "";
	    return created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	public long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	public String getReceiver_username() {
		return receiver_username;
	}
	public void setReceiver_username(String receiver_username) {
		this.receiver_username = receiver_username;
	}
	public String getSender_username() {
		return sender_username;
	}
	public void setSender_username(String sender_username) {
		this.sender_username = sender_username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public String getTarget_type() {
		return target_type;
	}
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "NotificationDTO [notificationId=" + notificationId + ", receiver_username=" + receiver_username
				+ ", sender_username=" + sender_username + ", type=" + type + ", content=" + content + ", targetId="
				+ targetId + ", target_type=" + target_type + ", read=" + read + ", created_at=" + created_at + "]";
	}	
}
