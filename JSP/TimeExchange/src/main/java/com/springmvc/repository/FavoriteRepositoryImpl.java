package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    JdbcTemplate template;

    @Autowired
    FavoriteRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * 찜 추가: 사용자가 특정 재능을 찜할 때 호출
     */
    @Override
    public void saveFavoriteTalent(FavoriteDTO favorite) {
        String sql = "INSERT INTO favorite_talent (member_id, talent_id) VALUES (?, ?)";
        template.update(sql, favorite.getMemberId(), favorite.getTalentId());
    }
       
    @Override
	public List<FavoriteDTO> readPagedFavoriteList(String memberId, int offset, int size) {
    	 String sql = """
    		        SELECT 
    		            f.favorite_id, 
    		            f.member_id, 
    		            f.talent_id, 
    		            f.created_at,
    		            t.title,
    		            t.category,
    		            t.description,
    		            t.timeSlot
    		        FROM favorite_talent f
    		        JOIN talent t ON f.talent_id = t.talent_id
    		        WHERE f.member_id = ?
    		        ORDER BY f.created_at DESC
    		        LIMIT ? OFFSET ?
    		    """;
    	 return template.query(sql, new FavoriteRowMapper(), memberId, size, offset);
	}

	@Override
	public int getFavoriteCount(String memberId) {
		String sql = "SELECT COUNT(*) FROM favorite_talent WHERE member_id = ?";
	    return template.queryForObject(sql, Integer.class, memberId);
	}

	
    

    /**
     * 찜 삭제: 사용자가 해당 재능의 찜을 해제할 때 호출
     */
    @Override
    public void deleteFavoriteTalent(String memberId, long talentId) {
        String sql = "DELETE FROM favorite_talent WHERE member_id = ? AND talent_id = ?";
        template.update(sql, memberId, talentId);
    }

    /**
     * 찜 여부 확인: 사용자가 해당 재능을 찜했는지 확인
     */
    @Override
    public boolean exists(String memberId, long talentId) {
        String sql = "SELECT COUNT(*) FROM favorite_talent WHERE member_id = ? AND talent_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, memberId, talentId);
        return count != null && count > 0;
    }
    
    
}
