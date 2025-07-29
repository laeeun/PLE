package com.springmvc.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.Member;
import com.springmvc.domain.ReportDTO;
import com.springmvc.service.NotificationService;
import com.springmvc.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
	@Autowired
	private NotificationService notificationService;
	@GetMapping
	public String showReportList(@RequestParam(value = "page", defaultValue = "1") int page,
	        					 @RequestParam(value = "size", defaultValue = "6") int size,
	        					 ReportDTO report,
	        					 Model model) {
		String targetType = report.getTarget_type();
		String status = report.getStatus();
		Map<String, Integer> reportCountMap = new HashMap<>();
		
		if (targetType != null && targetType.trim().isEmpty()) targetType = null;
	    if (status != null && status.trim().isEmpty()) status = null;
	    
		List<ReportDTO> reportlist = reportService.pagedReportList(targetType, status, page, size);
		for (ReportDTO r : reportlist) {
	        String targetId = r.getTarget_id();
	        int count = reportService.getReportCountForUser(targetId);
	        reportCountMap.put(targetId, count);
		}
		int totalCount = reportService.countReports(targetType, status);
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        System.out.println("📌 targetType: " + targetType + ", status: " + status);
        System.out.println("📌 reportlist size: " + reportlist.size());
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("reportlist", reportlist);
        model.addAttribute("targetType", targetType);
        model.addAttribute("status", status);
        model.addAttribute("reportCountMap", reportCountMap);
		return "ReportList";
	}
	
	@GetMapping("/view")
	public String showDetailReport(@RequestParam("id") int reportId,Model model) {
		ReportDTO report = reportService.readOneReport(reportId);
		model.addAttribute("report",report);
		return "DetailReport";
	}
	
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> addReport(HttpSession session, @RequestBody Map<String, String> body) {
	    Member user = (Member) session.getAttribute("loggedInUser");

	    ReportDTO report = new ReportDTO();
	    report.setReporter_id(user.getMember_id());
	    report.setTarget_id(body.get("target_id"));
	    report.setTarget_type(body.get("target_type"));
	    report.setTarget_ref_id(Integer.parseInt(body.get("target_ref_id")));
	    report.setReason(body.get("reason"));

	    // ✅ 먼저 중복 신고 여부 확인
	    boolean alreadyReported = reportService.isAlreadyReported(
	        report.getReporter_id(),
	        report.getTarget_type(),
	        report.getTarget_ref_id()
	    );

	    Map<String, Object> result = new HashMap<>();
	    if (alreadyReported) {
	        result.put("success", false);
	        result.put("message", "이미 신고한 항목입니다.");
	        return result;
	    }

	    reportService.saveReport(report);

	    result.put("success", true);
	    return result;
	}
	
	@PostMapping("/updatestatus")
	public String updateReportStatus(@RequestParam("reportId") int reportId,
	                                 @RequestParam("status") String status,
	                                 @RequestParam(value = "adminNote", required = false) String adminNote,
	                                 RedirectAttributes redirectAttributes) {
	    List<String> allowedStatuses = Arrays.asList("pending", "resolved", "rejected");

	    if (!allowedStatuses.contains(status)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "허용되지 않는 상태값입니다.");
	        return "redirect:/report";
	    }

	    if (adminNote == null || adminNote.trim().isEmpty()) {
	        adminNote = "처리 완료";
	    }

	    try {
	        reportService.updateReportStatus(reportId, status, adminNote);
	        redirectAttributes.addFlashAttribute("successMessage", "신고 상태가 성공적으로 변경되었습니다.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "상태 변경 중 오류가 발생했습니다.");
	        e.printStackTrace();
	    }

	    return "redirect:/report";
	}
	
	@PostMapping("/updatestatus/ajax")
	@ResponseBody
	public Map<String, Object> updateStatusAjax(@RequestParam int reportId,
	                                            @RequestParam String status,
	                                            @RequestParam(required = false) String adminNote) {
	    Map<String, Object> result = new HashMap<>();
	    List<String> allowedStatuses = Arrays.asList("pending", "resolved", "rejected");

	    if (!allowedStatuses.contains(status)) {
	        result.put("success", false);
	        result.put("message", "허용되지 않는 상태입니다.");
	        return result;
	    }

	    if (adminNote == null || adminNote.trim().isEmpty()) {
	        adminNote = "처리 완료";
	    }

	    try {
	        reportService.updateReportStatus(reportId, status, adminNote);
	        result.put("success", true);
	        result.put("message", "상태가 변경되었습니다.");
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "오류 발생: " + e.getMessage());
	    }

	    return result;
	}
	
	@GetMapping("/popup")
	public String showReportPopup(@RequestParam("target_id") String targetId,
	                              @RequestParam("target_type") String targetType,
	                              @RequestParam("target_ref_id") int targetRefId,
	                              Model model) {
	    model.addAttribute("target_id", targetId);
	    model.addAttribute("target_type", targetType);
	    model.addAttribute("target_ref_id", targetRefId);
	    return "reportPopup";  // /WEB-INF/views/reportPopup.jsp로 forward
	}
}
