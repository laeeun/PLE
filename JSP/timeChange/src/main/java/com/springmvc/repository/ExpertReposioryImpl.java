package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ExpertDTO;

@Repository
public class ExpertReposioryImpl implements ExpertReposiory {

    private final JdbcTemplate template;

    @Autowired
    public ExpertReposioryImpl(JdbcTemplate template) {
        this.template = template;
    }

    // 생성(Create)
    @Override
    public void create(ExpertDTO dto) {
        String sql = "INSERT INTO expert_board (expert_board_id, expert_id, title, content, category, price, available_time, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
            dto.getExpert_board_id(),
            dto.getExpert_id(),
            dto.getTitle(),
            dto.getContent(),
            dto.getCategory(),
            dto.getPrice(),
            dto.getAvailable_time(),
            dto.getCreated_at(),
            dto.getUpdated_at()
        );
    }

    // 단건 조회(Read One)
    @Override
    public ExpertDTO readOne(Long expert_board_id) {
        String sql = "SELECT * FROM expert_board WHERE expert_board_id = ?";
        return template.queryForObject(sql, new ExpertRowMapper(), expert_board_id);
    }

    // 수정(Update)
    @Override
    public void update(ExpertDTO dto) {
        String sql = "UPDATE expert_board SET title = ?, content = ?, category = ?, price = ?, available_time = ?, updated_at = NOW() WHERE expert_board_id = ?";
        template.update(sql,
            dto.getTitle(),
            dto.getContent(),
            dto.getCategory(),
            dto.getPrice(),
            dto.getAvailable_time(),
            dto.getExpert_board_id()
        );
    }

    // 삭제(Delete)
    @Override
    public void delete(Long expert_board_id) {
        String sql = "DELETE FROM expert_board WHERE expert_board_id = ?";
        template.update(sql, expert_board_id);
    }

    // 전체 목록 조회 (페이징)
    @Override
    public List<ExpertDTO> readPaged(int offset, int size) {
        String sql = "SELECT * FROM expert_board ORDER BY created_at DESC LIMIT ? OFFSET ?";
        return template.query(sql, new ExpertRowMapper(), size, offset);
    }

    // 전체 게시물 수
    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM expert_board";
        return template.queryForObject(sql, Integer.class);
    }

    // 카테고리별 목록 조회 (페이징)
    @Override
    public List<ExpertDTO> readPagedCategory(String category, int page, int size) {
        String sql = "SELECT * FROM expert_board WHERE category = ? ORDER BY created_at DESC LIMIT ?, ?";
        int offset = (page - 1) * size;
        return template.query(sql, new ExpertRowMapper(), category, offset, size);
    }

    // 카테고리별 게시물 수
    @Override
    public int getCountByCategory(String category) {
        String sql = "SELECT COUNT(*) FROM expert_board WHERE category = ?";
        return template.queryForObject(sql, Integer.class, category);
    }

    // 검색 결과 목록 조회 (페이징)
    @Override
    public List<ExpertDTO> searchPagedExpert(String keyword, int page, int size) {
        String sql = "SELECT * FROM expert_board WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC LIMIT ?, ?";
        String like = "%" + keyword + "%";
        int offset = (page - 1) * size;
        return template.query(sql, new ExpertRowMapper(), like, like, offset, size);
    }

    // 검색 결과 수
    @Override
    public int countSearchResult(String keyword) {
        String sql = "SELECT COUNT(*) FROM expert_board WHERE title LIKE ? OR content LIKE ?";
        String like = "%" + keyword + "%";
        return template.queryForObject(sql, Integer.class, like, like);
    }
}
