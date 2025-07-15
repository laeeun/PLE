package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    
}
