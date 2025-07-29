package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.HistoryDTO;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository{
	
    @Autowired
    private JdbcTemplate template;

	@Override
	public void save(HistoryDTO history) {
		
       String sql = "INSERT INTO history (buyer_id, seller_id, talent_id, category, account, balance_change, type, created_at)" + 
    		   		"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
       
       template.update(sql,
       history.getBuyer_id(),
       history.getSeller_id(),
       history.getTalent_id(),
       history.getCategory(),
       history.getAccount(),
       history.getBalance_change(),
       history.getType(),
       history.getCreated_at()   
    	);
        
	}

	@Override
	public List<HistoryDTO> findByMemberId(String member_id) {
		String sql = "SELECT * FROM history WHERE buyer_id = ? OR seller_id = ? ORDER BY created_at DESC";
		return template.query(sql, new Object[] {member_id, member_id}, new HistoryRowMapper());
	}
    
	@Override
	public HistoryDTO findById(Long historyId) {
	    String sql = "SELECT * FROM history WHERE history_id = ?";
	    try {
	        return template.queryForObject(sql, new Object[]{historyId}, new HistoryRowMapper());
	    } catch (EmptyResultDataAccessException e) {
	        return null;  // 해당 거래가 없으면 null 반환
	    }
	}
	
    @Override
    public boolean existsBySellerId(String sellerId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM history WHERE seller_id = ?)";
        Boolean exists = template.queryForObject(sql, Boolean.class, sellerId);
        return exists != null && exists;
    }

    @Override
    public boolean existsByBuyerId(String buyerId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM history WHERE buyer_id = ?)";
        Boolean exists = template.queryForObject(sql, Boolean.class, buyerId);
        return exists != null && exists;
    }

}
