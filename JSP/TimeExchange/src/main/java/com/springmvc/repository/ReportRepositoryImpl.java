package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ReportDTO;

@Repository
public class ReportRepositoryImpl implements ReportRepository{
	
	JdbcTemplate template;
	
	@Autowired
	ReportRepositoryImpl(JdbcTemplate template){
		this.template = template;
	}
	@Override
	public void saveReport(ReportDTO report) {
		String sql = "INSERT INTO report (reporter_id, target_id, target_type, target_ref_id, reason, status, created_at) "
	               + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    template.update(sql,
	        report.getReporter_id(),         // 신고자 ID
	        report.getTarget_id(),           // 신고 대상자 ID
	        report.getTarget_type(),         // 'talent', 'comment'
	        report.getTarget_ref_id(),       // 해당 게시글/댓글 ID
	        report.getReason(),              // 신고 사유
	        report.getStatus(),              // 기본값: 'pending'
	        report.getCreated_at()           // 생성 시간
	    );
	}

	@Override
	public List<ReportDTO> pagedReportList(String targetType, String status, int page, int size) {
	    String sql = "SELECT * FROM report WHERE target_type = ? AND status = ? " +
	                 "ORDER BY created_at DESC LIMIT ?, ?";

	    int offset = (page - 1) * size; // 페이지 번호는 1부터 시작한다고 가정

	    return template.query(sql, new ReportRowMapper(),
	            targetType,
	            status,
	            offset,
	            size
	    );
	}

	@Override
	public ReportDTO readOneReport(int reportId) {
		String sql = "SELECT * FROM report WHERE report_id=?";
		return template.queryForObject(sql, new ReportRowMapper(),reportId);
	}

	@Override
	public void updateReportStatus(int reportId, String status, String adminNote) {
	    String sql = "UPDATE report SET status = ?, admin_note = ?, processed_at = NOW() WHERE report_id = ?";
	    template.update(sql, status, adminNote, reportId);
	}

	@Override
	public boolean isAlreadyReported(String reporterId, String targetType, int targetRefId) {
	    String sql = "SELECT COUNT(*) FROM report WHERE reporter_id = ? AND target_type = ? AND target_ref_id = ?";
	    Integer count = template.queryForObject(sql, Integer.class, reporterId, targetType, targetRefId);
	    return count != null && count > 0;
	}

	@Override
	public int countReports(String targetType, String status) {
		 String sql = "SELECT COUNT(*) FROM report WHERE target_type = ? AND status = ?";
		 return template.queryForObject(sql, Integer.class, targetType, status);
	}
	
}
