package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.PasswordHistoryDTO;

public interface PasswordHistoryRepository {
	void save(PasswordHistoryDTO dto);
	
	List<PasswordHistoryDTO> findRecent3ByMemberId(String member_id);
}
