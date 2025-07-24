package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.PasswordHistoryDTO;

@Repository
public class PasswordHistoryRepositoryImpl implements PasswordHistoryRepository{
	@Autowired
	private JdbcTemplate template;

	@Override
	public void save(PasswordHistoryDTO dto) {
		String sql = "INSERT INTO password_history (member_id, password_hash) VALUES (?, ?)";
		template.update(sql, dto.getMember_id(), dto.getPassword_hash());
	}

	@Override
	public List<PasswordHistoryDTO> findRecent3ByMemberId(String member_id) {
		String sql = "SELECT * FROM password_history WHERE member_id = ? ORDER BY changed_at DESC LIMIT 3";
		return template.query(sql, new Object[] {member_id}, (rs, rowNum) -> {
			PasswordHistoryDTO dto = new PasswordHistoryDTO();
            dto.setId(rs.getInt("id"));
            dto.setMember_id(rs.getString("member_id"));
            dto.setPassword_hash(rs.getString("password_hash"));
            dto.setChanged_at(rs.getTimestamp("changed_at").toLocalDateTime());
            return dto;
		});
	}
	
	
	
	
}
