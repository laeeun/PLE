package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.ReportDTO;

public interface ReportRepository {
	// ✅ 신고 저장
    void saveReport(ReportDTO report);

    // ✅ 페이징 조회 (타입 + 상태별)
    List<ReportDTO> pagedReportList(String targetType, String status, int page, int size);

    // ✅ 상세 조회
    ReportDTO readOneReport(int reportId);

    // ✅ 상태 업데이트
    void updateReportStatus(int reportId, String status, String adminNote);

    // ✅ 중복 신고 체크
    boolean isAlreadyReported(String reporterId, String targetType, int targetRefId);

    // ✅ 총 개수 조회 (페이징용)
    int countReports(String targetType, String status);
	
    // 신고 대상 사용자의 신고 횟수 조회
    int countByTargetId(String targetId);
}
