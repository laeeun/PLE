package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.TalentDTO;

public interface TalentRepository {

	void create(TalentDTO dto);
    void update(TalentDTO dto);
    void delete(int talent_id);
    TalentDTO readone(long talent_id);
    List<TalentDTO> TalentByMemberId(String memberId);
    List<TalentDTO> findByAddrExceptUser(String baseAddr, String excludeUserId);
    List<TalentDTO> readTalents(int page, int size, String expert, String category, String keyword);
    int getTalentCount(String expert, String category, String keyword);
    List<String> findAllCategories();
    List<TalentDTO> findTopTalentsByRequestCount(String category, int limit);
}
