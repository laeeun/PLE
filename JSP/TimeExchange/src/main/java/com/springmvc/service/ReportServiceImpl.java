package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ReportDTO;
import com.springmvc.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService{
	@Autowired
	ReportRepository reportRepository;
	
	@Override
	public void saveReport(ReportDTO report) {
		reportRepository.saveReport(report);
	}

	@Override
	public List<ReportDTO> pagedReportList(String targetType, String status, int page, int size) {
		return reportRepository.pagedReportList(targetType, status, page, size);
	}

	@Override
	public ReportDTO readOneReport(int reportId) {
		return reportRepository.readOneReport(reportId);
	}

	@Override
	public void updateReportStatus(int reportId, String status, String adminNote) {
		reportRepository.updateReportStatus(reportId, status, adminNote);
		
	}

	@Override
	public boolean isAlreadyReported(String reporterId, String targetType, int targetRefId) {		
		return reportRepository.isAlreadyReported(reporterId, targetType, targetRefId);
	}

	@Override
	public int countReports(String targetType, String status) {
		return reportRepository.countReports(targetType, status);
	}
	
}
