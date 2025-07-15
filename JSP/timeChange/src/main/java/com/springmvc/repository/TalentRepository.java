package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.TalentDTO;

public interface TalentRepository {

    // 생성(Create)
    public void create(TalentDTO dto);

    // 단건 조회(Read)
    public TalentDTO readone(long id);

    // 전체 조회 (비권장 - 페이징 처리된 readPaged 사용 권장)
    public List<TalentDTO> readAll();

    // 수정(Update)
    public void update(TalentDTO dto);

    // 삭제(Delete)
    public void delete(int id);

    // 카테고리별 조회 (페이징 없이 단순 필터)
    public List<TalentDTO> TalentByCategory(String category);

    // 검색 조회 (페이징 없이 단순 필터)
    public List<TalentDTO> searchTalent(String keyword);

    // 전체 목록 조회 (페이징 처리)
    public List<TalentDTO> readPaged(int offset, int size);

    // 전체 게시물 수 (페이징 계산용)
    public int countAll();
    //멤버 아이디로 재능 조회
    public List<TalentDTO> TalentByMemberId(String memberId);
    // 카테고리별 목록 조회 (페이징 처리)
    public List<TalentDTO> readPagedCategory(String category, int page, int size);

    // 카테고리별 게시물 수 (페이징 계산용)
    public int getCountByCategory(String category);

    // 검색 결과 목록 조회 (페이징 처리)
    public List<TalentDTO> searchPagedTalent(String keyword, int page, int size);

    // 검색 결과 수 (페이징 계산용)
    public int countSearchResult(String keyword);
}
