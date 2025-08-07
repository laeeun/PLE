package com.springmvc.domain;

import java.time.LocalDateTime;

import com.springmvc.util.DateTimeUtils;

public class ReviewDTO{
	
    private Long reviewId;          // 리뷰 고유 ID
    
    private String writerName;        // 리뷰 작성자 ID
    
    private String targetName;        // 리뷰 대상자 ID (EXPERT)
    
    private Long talentId;          // 어떤 재능에 대한 리뷰인지
    
    private int rating;             // 평점 (1~5)
    
    private String comment;         // 리뷰 본문
    
    private String category;
    
    private LocalDateTime createdAt; // 작성 시간
    
    private Long historyId;
    
    private ReviewReplyDTO reply; // 판매자 답글 정보
    
    private int likeCount;     // 좋아요 개수
    
    private int dislikeCount;  // 싫어요 개수
    
    private String myReaction; // 현재 로그인한 사용자의 반응 ("LIKE", "DISLIKE", 또는 null)
    
    public String getFormattedCreatedAt() {
        return DateTimeUtils.format(this.createdAt);
    }
    
    // Getter & Setter 모음

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public ReviewReplyDTO getReply() {
		return reply;
	}

	public void setReply(ReviewReplyDTO reply) {
		this.reply = reply;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	public String getMyReaction() {
		return myReaction;
	}

	public void setMyReaction(String myReaction) {
		this.myReaction = myReaction;
	}

	@Override
	public String toString() {
		return "ReviewDTO [reviewId=" + reviewId + ", writerId=" + writerName + ", targetId=" + targetName + ", talentId="
				+ talentId + ", rating=" + rating + ", comment=" + comment + ", category=" + category + ", createdAt="
				+ createdAt + ", historyId=" + historyId + ", reply=" + reply + ", likeCount=" + likeCount
				+ ", dislikeCount=" + dislikeCount + ", myReaction=" + myReaction + "]";
	}

}
