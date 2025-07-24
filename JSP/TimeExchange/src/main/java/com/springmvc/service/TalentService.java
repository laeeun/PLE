package com.springmvc.service;

import java.util.List;
import com.springmvc.domain.TalentDTO;

public interface TalentService {
	void create(TalentDTO dto);
    void update(TalentDTO dto);
    void delete(int talent_id);
    TalentDTO readone(long talentId);
    List<TalentDTO> TalentByMemberId(String memberId);
    List<String> getAllCategories();
    void formatTimeSlot(TalentDTO dto);
    List<TalentDTO> getTalentByAddr(String userId, String baseAddr);
    List<TalentDTO> readTalents(int page, int size, String expert, String category, String keyword);
    int getTalentCount(String expert, String category, String keyword);
    List<TalentDTO> getTopTalentsByRequestCount(String category, int limit);
}
