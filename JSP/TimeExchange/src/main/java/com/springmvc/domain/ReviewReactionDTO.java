package com.springmvc.domain;

import java.time.LocalDateTime;

public class ReviewReactionDTO {
    private Long id;
    private Long reviewId;
    private String memberId;
    private String reactionType; // "LIKE" or "DISLIKE"
    private LocalDateTime createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getReactionType() {
		return reactionType;
	}
	public void setReactionType(String reactionType) {
		this.reactionType = reactionType;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ReviewReactionDTO [id=" + id + ", reviewId=" + reviewId + ", memberId=" + memberId + ", reactionType="
				+ reactionType + ", createdAt=" + createdAt + "]";
	}
    
    
}
