package com.springmvc.domain;

import java.time.LocalDateTime;

public class ReviewReplyDTO {
    private Long replyId;
    private Long reviewId;
    private String sellerId;
    private String content;
    private LocalDateTime createdAt;

    // ✅ 좋아요/싫어요 관련 추가 필드
    private int likeCount;
    private int dislikeCount;
    private String myReaction;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
        return "ReviewReplyDTO [replyId=" + replyId + ", reviewId=" + reviewId + ", sellerId=" + sellerId
                + ", content=" + content + ", createdAt=" + createdAt
                + ", likeCount=" + likeCount + ", dislikeCount=" + dislikeCount
                + ", myReaction=" + myReaction + "]";
    }
}
