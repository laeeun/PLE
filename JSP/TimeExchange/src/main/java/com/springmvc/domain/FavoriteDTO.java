package com.springmvc.domain;

import java.time.LocalDateTime;

public class FavoriteDTO {
	private long favoriteId;
	private String memberId;
	private long talentId;
	LocalDateTime created_at = LocalDateTime.now();
	private String title;
	private String category;
	private String description;
	private int timeSlot;
	
	
	public FavoriteDTO() {}
	
	public FavoriteDTO(long favoriteId, String memberId, long talentId, LocalDateTime created_at) {
		super();
		this.favoriteId = favoriteId;
		this.memberId = memberId;
		this.talentId = talentId;
		this.created_at = created_at;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	public long getFavoriteId() {
		return favoriteId;
	}
	public void setFavoriteId(long favoriteId) {
		this.favoriteId = favoriteId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public long getTalentId() {
		return talentId;
	}
	public void setTalentId(long talentId) {
		this.talentId = talentId;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	
	@Override
	public String toString() {
		return "FavoriteDTO [favoriteId=" + favoriteId + ", memberId=" + memberId + ", talentId=" + talentId
				+ ", created_at=" + created_at + "]";
	}	
}
