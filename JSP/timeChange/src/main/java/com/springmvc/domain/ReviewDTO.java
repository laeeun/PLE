package com.springmvc.domain;

import java.time.LocalDateTime;

public class ReviewDTO{
	
    private Long reviewId;          // 리뷰 고유 ID
    
    private String writerId;        // 리뷰 작성자 ID
    
    private String targetId;        // 리뷰 대상자 ID (EXPERT)
    
    private Long talentId;          // 어떤 재능에 대한 리뷰인지
    
    private int rating;             // 평점 (1~5)
    
    private String comment;         // 리뷰 본문
    
    private LocalDateTime createdAt; // 작성 시간
    
    private Long historyId;

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Long getTalentId() {
		return talentId;
	}

	public void setTalentId(Long talentId) {
		this.talentId = talentId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", writerId=" + writerId + ", targetId=" + targetId + ", talentId="
				+ talentId + ", rating=" + rating + ", comment=" + comment + ", createdAt=" + createdAt + "]";
	}

	
    
    
}
