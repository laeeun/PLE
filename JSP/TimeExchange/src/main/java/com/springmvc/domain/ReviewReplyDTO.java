package com.springmvc.domain;

import java.time.LocalDateTime;

public class ReviewReplyDTO {
    private Long replyId;          // 답글 고유 ID
    private Long reviewId;         // 원본 리뷰 ID
    private String sellerId;       // 답글 작성자 (판매자) ID
    private String replyContent;        // 답글 내용
    private LocalDateTime replyCreatedAt; // 답글 작성 시간
    
    public String getFormattedReplyCreatedAt() {
        return com.springmvc.util.DateTimeUtils.format(this.replyCreatedAt);
    }
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getReplyContent() {
		return replyContent; 
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent; 
	}

	public LocalDateTime getReplyCreatedAt() {
		return replyCreatedAt; 
	}
	public void setReplyCreatedAt(LocalDateTime replyCreatedAt) {
		this.replyCreatedAt = replyCreatedAt; 
	}
	@Override
	public String toString() {
		return "ReviewReplyDTO [replyId=" + replyId + ", reviewId=" + reviewId + ", sellerId=" + sellerId
				+ ", replyContent=" + replyContent + ", replyCreatedAt=" + replyCreatedAt + "]";
	}

	
	

    
}
