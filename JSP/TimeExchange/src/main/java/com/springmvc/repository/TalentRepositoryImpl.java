package com.springmvc.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TalentDTO;

@Repository
public class TalentRepositoryImpl implements TalentRepository {

    @Autowired
    private JdbcTemplate template;

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

    @Override
    public void update(TalentDTO dto) {
        String sql = "UPDATE talent SET title=?, description=?, category=?, timeSlot=? WHERE talent_id=?";
           template.update(sql,
               dto.getTitle(),
               dto.getDescription(),
               dto.getCategory(),
               dto.getTimeSlot(),
               dto.getTalent_id()
           );
    }

    @Override
    public void delete(int talent_id) {
        String sql = "DELETE FROM talent WHERE talent_id = ?";
        template.update(sql, talent_id);
    }

    @Override
    public TalentDTO readone(long talent_id) {
    	String sql = """
    	        SELECT t.*, m.username, m.expert
    	        FROM talent t
    	        JOIN member m ON t.member_id = m.member_id
    	        WHERE t.talent_id = ?
    	    """;
        return template.queryForObject(sql, new TalentRowMapper(), talent_id);
    }
    
    @Override
	public List<TalentDTO> TalentByMemberId(String memberId) {
    	String sql = """
    		    SELECT t.*, m.username, m.expert
    		    FROM talent t
    		    JOIN member m ON t.member_id = m.member_id
    		    WHERE t.member_id = ?
    		    ORDER BY t.created_at DESC
    		""";
        return template.query(sql, new Object[]{memberId}, new TalentRowMapper());
	}

    @Override
    public List<TalentDTO> readTalents(int page, int size, String expert, String category, String keyword) {
        StringBuilder sql = new StringBuilder("""
            SELECT t.*, m.username, m.expert
            FROM talent t
            JOIN member m ON t.member_id = m.member_id
            WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        // 전문가 필터
        if (!"all".equals(expert)) {
            sql.append(" AND m.expert = ?");
            params.add("true".equals(expert) ? 1 : 0);
        }

        // 카테고리 필터
        if (category != null && !category.isEmpty()) {
            sql.append(" AND t.category = ?");
            params.add(category);
        }

        // 키워드 검색
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (t.title LIKE ? OR t.description LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        // 정렬 + 페이징
        sql.append(" ORDER BY t.created_at DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add((page - 1) * size);

        return template.query(sql.toString(), new TalentRowMapper(), params.toArray());
    }

    @Override
    public int getTalentCount(String expert, String category, String keyword) {
    	StringBuilder sql = new StringBuilder("""
    	        SELECT COUNT(*)
    	        FROM talent t
    	        JOIN member m ON t.member_id = m.member_id
    	        WHERE 1=1
    	    """);
        List<Object> params = new ArrayList<>();

        if (!"all".equals(expert)) {
            sql.append(" AND expert = ?");
            params.add("true".equals(expert) ? 1 : 0);
        }

        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
            params.add(category);
        }

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (title LIKE ? OR description LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        return template.queryForObject(sql.toString(), Integer.class, params.toArray());
    }

    @Override
    public List<TalentDTO> findByAddrExceptUser(String baseAddr, String excludeUserId) {
    	String sql = """
    		    SELECT t.*, m.username, m.expert
    		    FROM talent t
    		    JOIN member m ON t.member_id = m.member_id
    		    WHERE t.addr LIKE ? AND t.member_id <> ?
    		    ORDER BY t.created_at DESC
    		""";
        return template.query(sql, new Object[]{"%" + baseAddr + "%", excludeUserId}, new TalentRowMapper());
    }

	@Override
	public List<String> findAllCategories() {
		 String sql = "SELECT DISTINCT category FROM talent ORDER BY category ASC";
		 return template.queryForList(sql, String.class);
	}
	@Override
	public List<TalentDTO> findTopTalentsByRequestCount(String category, int limit) {
	    String sql = """
	        SELECT t.*, m.username, m.expert, COUNT(pr.request_id) AS request_count
	        FROM talent t
	        JOIN member m ON t.member_id = m.member_id
	        LEFT JOIN purchase_request pr ON t.talent_id = pr.talent_id
	        WHERE t.category = ?
	        GROUP BY t.talent_id
	        ORDER BY request_count DESC
	        LIMIT ?
	    """;
	    return template.query(sql, new Object[]{category, limit}, new TalentRowMapper());
	}
    
}
