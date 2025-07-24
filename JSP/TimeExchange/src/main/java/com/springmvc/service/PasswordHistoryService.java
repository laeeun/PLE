package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.PasswordHistoryDTO;

public interface PasswordHistoryService {
	
	void save(PasswordHistoryDTO dto);
	
	List<PasswordHistoryDTO> findRecent3ByMemberId(String member_id);
}
