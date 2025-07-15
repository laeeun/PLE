package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.springmvc.domain.TalentDTO;

@Repository
public class TalentRepositoryImpl implements TalentRepository {

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // 등록(Create)
    @Override
    public void create(TalentDTO dto) {
        String sql = "INSERT INTO talent (member_id, title, description, category, created_at, timeSlot) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql,
            dto.getMember_id(),
            dto.getTitle(),
            dto.getDescription(),
            dto.getCategory(),
            Timestamp.valueOf(dto.getCreated_at()),
            dto.getTimeSlot()
        );
    }

    // 멤버아이디로 조회
    @Override
    public List<TalentDTO> TalentByMemberId(String memberId) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.member_id = ? ORDER BY t.created_at DESC";
        return template.query(sql, new TalentRowMapper(), memberId);
    }

    // 단건 조회(Read)
    @Override
    public TalentDTO readone(long id) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.talent_id = ?";
        return template.queryForObject(sql, new TalentRowMapper(), id);
    }

    // 전체 목록 조회 (비권장, 페이징 처리된 readPaged 사용 권장)
    @Override
    public List<TalentDTO> readAll() {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id ORDER BY t.created_at DESC";
        return template.query(sql, new TalentRowMapper());
    }

    // 수정(Update)
    @Override
    public void update(TalentDTO dto) {
        String sql = "UPDATE talent SET title = ?, description = ?, category = ?, timeSlot = ? WHERE talent_id = ?";
        try {
            template.update(sql,
                dto.getTitle(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getTimeSlot(),
                dto.getTalent_id()
            );
        } catch (DataAccessException e) {
            System.err.println("수정 중 오류 발생: " + e.getMessage());
        }
    }

    // 삭제(Delete)
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM talent WHERE talent_id = ?";
        try {
            template.update(sql, id);
        } catch (DataAccessException e) {
            System.err.println("삭제 중 오류 발생: " + e.getMessage());
        }
    }

    // 카테고리별 전체 목록 조회
    @Override
    public List<TalentDTO> TalentByCategory(String category) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.category = ?";
        return template.query(sql, new TalentRowMapper(), category);
    }

    // 검색어 기반 전체 목록 조회
    @Override
    public List<TalentDTO> searchTalent(String keyword) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.title LIKE ? OR t.description LIKE ?";
        String param = "%" + keyword + "%";
        return template.query(sql, new TalentRowMapper(), param, param);
    }

    // 전체 목록 조회 (페이징 처리)
    @Override
    public List<TalentDTO> readPaged(int offset, int size) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id ORDER BY t.created_at DESC LIMIT ? OFFSET ?";
        return template.query(sql, new TalentRowMapper(), size, offset);
    }

    // 전체 게시물 수 조회
    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM talent";
        return template.queryForObject(sql, Integer.class);
    }

    // 카테고리별 목록 조회 (페이징 처리)
    @Override
    public List<TalentDTO> readPagedCategory(String category, int page, int size) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.category = ? ORDER BY t.created_at DESC LIMIT ?, ?";
        int offset = (page - 1) * size;
        return template.query(sql, new TalentRowMapper(), category, offset, size);
    }

    // 카테고리별 게시물 수 조회
    @Override
    public int getCountByCategory(String category) {
        String sql = "SELECT COUNT(*) FROM talent WHERE category = ?";
        return template.queryForObject(sql, Integer.class, category);
    }

    // 검색어 기반 목록 조회 (페이징 처리)
    @Override
    public List<TalentDTO> searchPagedTalent(String keyword, int page, int size) {
        String sql = "SELECT t.*, m.username, m.expert FROM talent t JOIN member m ON t.member_id = m.member_id WHERE t.title LIKE ? OR t.description LIKE ? ORDER BY t.created_at DESC LIMIT ?, ?";
        String like = "%" + keyword + "%";
        int offset = (page - 1) * size;
        return template.query(sql, new TalentRowMapper(), like, like, offset, size);
    }

    // 검색어 기반 게시물 수 조회
    @Override
    public int countSearchResult(String keyword) {
        String sql = "SELECT COUNT(*) FROM talent WHERE title LIKE ? OR description LIKE ?";
        String like = "%" + keyword + "%";
        return template.queryForObject(sql, Integer.class, like, like);
    }

    // MySQL 커넥션 클린업 처리
    @PreDestroy
    public void cleanUpMySQLConnectionThread() {
        AbandonedConnectionCleanupThread.checkedShutdown();
        System.out.println("MySQL cleanup thread 종료 완료");
    }
}
