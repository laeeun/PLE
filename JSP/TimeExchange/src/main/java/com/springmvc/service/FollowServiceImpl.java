package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.FollowDTO;
import com.springmvc.repository.FollowRepository;

@Service
public class FollowServiceImpl implements FollowService{

	@Autowired
	private FollowRepository followRepository;

	@Override
	public void addFollow(FollowDTO dto) {
		followRepository.addFollow(dto);
	}

	@Override
	public void deleteFollow(String followerId, String followingId) {
		followRepository.deleteFollow(followerId, followingId);		
	}

	@Override
	public List<FollowDTO> findFollowingList(String followerId) {
		return followRepository.findFollowingList(followerId);
	}

	@Override
	public List<FollowDTO> findFollowerList(String followingId) {
		return followRepository.findFollowerList(followingId);
	}

	@Override
	public boolean exists(String followerId, String followingId) {
		return followRepository.exists(followerId, followingId);
	}

	@Override
	public int countFollowers(String userId) {
		return followRepository.countFollowers(userId);
	}

	@Override
	public int countFollowing(String userId) {
		return followRepository.countFollowing(userId);
	}

	@Override
	public boolean toggleFollow(String followerId, String followingId) {
	    if (followRepository.exists(followerId, followingId)) {
	        followRepository.deleteFollow(followerId, followingId);
	        return false; // 언팔로우 완료
	    } else {
	        FollowDTO dto = new FollowDTO();
	        dto.setFollower_id(followerId);
	        dto.setFollowing_id(followingId);
	        followRepository.addFollow(dto);
	        return true; // 팔로우 완료
	    }
	}
	
	
}
