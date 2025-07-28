package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.FollowDTO;

public interface FollowService {
	 // ✅ 팔로우 추가
    void addFollow(FollowDTO dto);
    
    // ✅ 팔로우 취소 → 어떤 팔로우를 삭제할지 지정해야 함
    void deleteFollow(String followerId, String followingId);
    
    // ✅ 내가 팔로우한 사용자 목록 (마이페이지 등)
    List<FollowDTO> findFollowingList(String followerId); // 내가 팔로우한 사람들
    
    // ✅ 나를 팔로우한 사용자 목록 (역방향 팔로워 조회도 종종 필요함)
    List<FollowDTO> findFollowerList(String followingId); // 나를 팔로우한 사람들
    
    // ✅ 팔로우 여부 확인 (이미 팔로우한 상태인지)
    boolean exists(String followerId, String followingId);
    
    // ✅ 팔로우 수 카운트 (마이페이지에서 숫자 표시용)
    int countFollowers(String userId);     // 나를 팔로우한 사람 수
    int countFollowing(String userId);     // 내가 팔로우한 사람 
    
    // ✅ 팔로우 상태 토글 (찜처럼 사용)
    boolean toggleFollow(String followerId, String followingId);
}
