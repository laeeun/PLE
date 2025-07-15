package com.springmvc.service;

import java.util.List;
import com.springmvc.domain.TalentDTO;

public interface TalentService {

    // 새로운 재능 등록
    public void create(TalentDTO dto);

    // ID로 재능 하나 조회
    public TalentDTO readone(long id);

    // 모든 재능 목록 조회
    public List<TalentDTO> readAll();

    // 재능 정보 수정
    public void update(TalentDTO dto);

    // 재능 삭제
    public void delete(int id);
    //멤버 아이디로 재능목록 조회
    public List<TalentDTO> TalentByMemberId(String memberId);
    
    // 특정 카테고리의 재능 목록 조회
    public List<TalentDTO> TalentByCategory(String category);

    // 키워드로 재능 검색 (제목, 설명 기준)
    public List<TalentDTO> searchTalent(String keyword);

    // 페이징된 전체 재능 목록 조회
    public List<TalentDTO> readPagedList(int page, int size);

    // 전체 재능 개수 반환 (페이징용)
    public int getCountAll();

    // 페이징된 특정 카테고리 재능 목록 조회
    public List<TalentDTO> readPagedCategory(String category, int page, int size);

    // 특정 카테고리의 총 재능 수 반환
    public int getCountByCategory(String category);

    // 페이징된 키워드 검색 결과 조회
    public List<TalentDTO> searchPagedTalent(String keyword, int page, int size);

    // 키워드 검색 결과 총 개수 반환
    public int countSearchResult(String keyword);
    
    //시간 변환 로직
    public void formatTimeSlot(TalentDTO dto);
    
}
