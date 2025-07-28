package com.springmvc.domain;

import java.time.LocalDateTime;

public class FollowDTO {
	String follower_id; //팔로우하는 유저
	String following_id; //팔로우 당하는 유저
	LocalDateTime created_at = LocalDateTime.now();
	
	private String userName;
    private String profileImage;
	public FollowDTO() {}
	public FollowDTO(String follower_id, String following_id, LocalDateTime created_at) {
		super();
		this.follower_id = follower_id;
		this.following_id = following_id;
		this.created_at = created_at;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getFollower_id() {
		return follower_id;
	}
	public void setFollower_id(String follower_id) {
		this.follower_id = follower_id;
	}
	public String getFollowing_id() {
		return following_id;
	}
	public void setFollowing_id(String following_id) {
		this.following_id = following_id;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "FollowDTO [follower_id=" + follower_id + ", following_id=" + following_id + ", created_at=" + created_at
				+ "]";
	}	
}
