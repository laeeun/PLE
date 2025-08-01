package com.springmvc.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

public class TalentDTO {
	private long talent_id;
	private	String member_id;
	private String title;
	private String description;
	private String category;
	private LocalDateTime created_at = LocalDateTime.now();
	private int timeSlot;
	private String timeSlotDisplay;
	private String username; //닉네임
	private boolean expert;
	private int RequestCount;
	private boolean favorite;
	private double averageRating;
	private int reviewCount;
	private MultipartFile uploadFile;
	private String filename;
	public TalentDTO() {}
	public TalentDTO(int talent_id, String member_id, String title, String description, String category,
			LocalDateTime created_at, int timeSlot) {
		this.talent_id = talent_id;
		this.member_id = member_id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.created_at = created_at;
		this.timeSlot = timeSlot;
	}
	
	public String getFormattedCreatedAt() {
	    if (created_at == null) return "";
	    return created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
		
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public int getRequestCount() {
		return RequestCount;
	}
	public void setRequestCount(int requestCount) {
		RequestCount = requestCount;
	}
	public boolean isExpert() {
		return expert;
	}
	public void setExpert(boolean expert) {
		this.expert = expert;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTimeSlotDisplay() {
		return timeSlotDisplay;
	}
	public void setTimeSlotDisplay(String timeSlotDisplay) {
		this.timeSlotDisplay = timeSlotDisplay;
	}
	public int getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}	
	public long getTalent_id() {
		return talent_id;
	}
	public void setTalent_id(long talent_id) {
		this.talent_id = talent_id;
	}
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "TalentDTO [talent_id=" + talent_id + ", member_id=" + member_id + ", title=" + title + ", description="
				+ description + ", category=" + category + ", created_at=" + created_at + ", timeSlot=" + timeSlot
				+ ", timeSlotDisplay=" + timeSlotDisplay + "]";
	}

	
	
	
}
