package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.FollowDTO;

@Repository
public class FollowRepositoryImpl implements FollowRepository{
	
	private final JdbcTemplate template;
	
	@Autowired
	FollowRepositoryImpl(JdbcTemplate template){
		this.template = template;
	}

	@Override
	public void addFollow(FollowDTO dto) {
		String sql = "INSERT INTO follow (follower_id, following_id) VALUES (?, ?)";
	    template.update(sql, dto.getFollower_id(), dto.getFollowing_id());
	}
	
	@Override
	public void deleteFollow(String followerId, String followingId) {
		String sql = "DELETE FROM follow WHERE follower_id = ? AND following_id = ?";
	    template.update(sql, followerId, followingId);
	}

	@Override
	public List<FollowDTO> findFollowingList(String followerId) {
		 String sql = "SELECT f.follower_id, f.following_id, f.created_at, " +
                 "m.username, m.profile_image " +
                 "FROM follow f " +
                 "JOIN member m ON f.following_id = m.member_id " +
                 "WHERE f.follower_id = ?";
	    return template.query(sql, new FollowRowMapper(), followerId);
	}

	@Override
	public List<FollowDTO> findFollowerList(String followingId) {
		String sql = "SELECT f.follower_id, f.following_id, f.created_at, " +
                "m.username, m.profile_image " +
                "FROM follow f " +
                "JOIN member m ON f.follower_id = m.member_id " +
                "WHERE f.following_id = ?";
		return template.query(sql, new FollowRowMapper(), followingId);
	}

	@Override
	public boolean exists(String followerId, String followingId) {
		String sql = "SELECT COUNT(*) FROM follow WHERE follower_id = ? AND following_id = ?";
	    Integer count = template.queryForObject(sql, Integer.class, followerId, followingId);
	    return count != null && count > 0;
	}

	@Override
	public int countFollowers(String userId) {
		String sql = "SELECT COUNT(*) FROM follow WHERE following_id  = ?";
		return template.queryForObject(sql, Integer.class, userId);
	}

	@Override
	public int countFollowing(String userId) {
		String sql = "SELECT COUNT(*) FROM follow WHERE follower_id  = ?";
		return template.queryForObject(sql, Integer.class, userId);
	}
	
}
