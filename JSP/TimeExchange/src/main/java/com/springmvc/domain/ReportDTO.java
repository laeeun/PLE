package com.springmvc.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportDTO {
    private int report_id; // 신고 고유 번호 (PK)
    private String reporter_id; // 신고한 사람의 member_id (로그인한 사용자)
    private String target_id; // 신고 대상 사용자의 member_id (예: 댓글 작성자, 게시글 작성자)
    private String target_type = "talent"; // 신고 대상 타입 ('talent', 'comment' 등)
    private int target_ref_id; // 신고 대상의 고유 ID (예: talent_id, comment_id)
    private String reason; // 사용자가 입력한 신고 사유
    private String status = "pending"; // 신고 처리 상태 ('pending', 'resolved', 'rejected')
    private LocalDateTime created_at = LocalDateTime.now(); // 신고 등록 시각
    private LocalDateTime processed_at; // 관리자에 의해 처리된 시각 (nullable)
    private String admin_note; // 관리자 메모 또는 처리 결과 (nullable)
	    
    public ReportDTO(int report_id, String reporter_id, String target_id, String target_type, int target_ref_id,
			String reason, String status, LocalDateTime created_at, LocalDateTime processed_at, String admin_note) {
		super();
		this.report_id = report_id;
		this.reporter_id = reporter_id;
		this.target_id = target_id;
		this.target_type = target_type;
		this.target_ref_id = target_ref_id;
		this.reason = reason;
		this.status = status;
		this.created_at = created_at;
		this.processed_at = processed_at;
		this.admin_note = admin_note;
	}
	public ReportDTO() {}
	
	public String getFormattedCreatedAt() {
	    if (created_at == null) return "";
	    return created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
	
	public int getReport_id() {
		return report_id;
	}
	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}
	public String getReporter_id() {
		return reporter_id;
	}
	public void setReporter_id(String reporter_id) {
		this.reporter_id = reporter_id;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public String getTarget_type() {
		return target_type;
	}
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}
	public int getTarget_ref_id() {
		return target_ref_id;
	}
	public void setTarget_ref_id(int target_ref_id) {
		this.target_ref_id = target_ref_id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getProcessed_at() {
		return processed_at;
	}
	public void setProcessed_at(LocalDateTime processed_at) {
		this.processed_at = processed_at;
	}
	public String getAdmin_note() {
		return admin_note;
	}
	public void setAdmin_note(String admin_note) {
		this.admin_note = admin_note;
	}
	@Override
	public String toString() {
		return "ReportDTO [report_id=" + report_id + ", reporter_id=" + reporter_id + ", target_id=" + target_id
				+ ", target_type=" + target_type + ", target_ref_id=" + target_ref_id + ", reason=" + reason
				+ ", status=" + status + ", created_at=" + created_at + ", processed_at=" + processed_at
				+ ", admin_note=" + admin_note + "]";
	}
    
    
}
