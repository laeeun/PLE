package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.ExpertDTO;
import com.springmvc.domain.TalentDTO;

public interface ExpertReposiory {
	// 생성(Create)
	public void create(ExpertDTO dto);
	// 단건 조회(readOne)
	public ExpertDTO readOne(Long expert_board_id);
	// 업데이트(update)
	public void update(ExpertDTO dto);
	// 게시글 삭제(delete)
	public void delete(Long expert_board_id);
	
	// 전체 목록 조회 (페이징 처리)
    public List<ExpertDTO> readPaged(int offset, int size);

    // 전체 게시물 수 (페이징 계산용)
    public int countAll();

    // 카테고리별 목록 조회 (페이징 처리)
    public List<ExpertDTO> readPagedCategory(String category, int page, int size);

    // 카테고리별 게시물 수 (페이징 계산용)
    public int getCountByCategory(String category);

    // 검색 결과 목록 조회 (페이징 처리)
    public List<ExpertDTO> searchPagedExpert(String keyword, int page, int size);

    // 검색 결과 수 (페이징 계산용)
    public int countSearchResult(String keyword);
}
