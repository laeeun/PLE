package com.springmvc.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.GoalDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.GoalService;

@Controller
@RequestMapping("/goals")
public class GoalController {
    
    @Autowired
    private GoalService goalService;
    
    // 목표 목록 페이지
    @GetMapping
    public String goalsPage(HttpSession session, Model model) {    	
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<GoalDTO> weeklyGoals = goalService.findActiveGoalsByType(user.getMember_id(), "WEEKLY");
        List<GoalDTO> monthlyGoals = goalService.findActiveGoalsByType(user.getMember_id(), "MONTHLY");

        model.addAttribute("weeklyGoals", weeklyGoals);
        model.addAttribute("monthlyGoals", monthlyGoals);
        
        return "goals";
    }
    
    // 목표 생성
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createGoal(@RequestBody GoalDTO goal, HttpSession session) {
    	Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }
        
        try {
            goal.setUserId(user.getMember_id());
            goalService.createGoal(goal);
            
            return Map.of("success", true, "message", "목표가 생성되었습니다.");
        } catch (Exception e) {
            return Map.of("success", false, "message", "목표 생성에 실패했습니다: " + e.getMessage());
        }
    }
    
    // 목표 업데이트
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateGoal(@RequestBody GoalDTO goal, HttpSession session) {
        System.out.println("updateGoal입장");
        System.out.println("updateGoal DTO:"+goal.toString());
    	Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }
        
        try {
            goal.setUserId(user.getMember_id());
            goalService.updateGoal(goal);
            
            return Map.of("success", true, "message", "목표가 업데이트되었습니다.");
        } catch (Exception e) {
            return Map.of("success", false, "message", "목표 업데이트에 실패했습니다: " + e.getMessage());
        }
    }
    
    // 목표 삭제
    @PostMapping("/delete/{goalId}")
    @ResponseBody
    public Map<String, Object> deleteGoal(@PathVariable Long goalId, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }
        
        try {
            goalService.deleteGoal(goalId, user.getMember_id());
            return Map.of("success", true, "message", "목표가 삭제되었습니다.");
        } catch (Exception e) {
            return Map.of("success", false, "message", "목표 삭제에 실패했습니다: " + e.getMessage());
        }
    }
    
    @GetMapping("/progress")
    @ResponseBody
    public Map<String, Object> getGoalProgress(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");

        if (user == null) return Map.of();

        String memberId = user.getMember_id();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        LocalDate weekStart = now.with(DayOfWeek.MONDAY);

        Map<String, Object> monthly = goalService.getMonthlyGoalProgress(memberId, year, month);
        Map<String, Object> weekly = goalService.getWeeklyGoalProgress(memberId, weekStart);

        return Map.of(
            "monthly", monthly,
            "weekly", weekly
        );
    }
    
    @PostMapping("/toggle")
    @ResponseBody
    public ResponseEntity<String> toggleGoalCompletion(@RequestParam("goalId") Long goalId, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            int result = goalService.toggleGoalCompletion(goalId); // → 서비스 계층에서 호출
            if (result == 1) {
                return ResponseEntity.ok("완료 상태가 변경되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 목표를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }
    
    @GetMapping("/history")
    public String getPastGoals(@RequestParam String type,
                               @RequestParam String param,
                               Model model,
                               HttpSession session) {

        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return "fragments/loginRequired";

        LocalDate start, end;
        if (type.equals("MONTHLY")) {
            YearMonth ym = YearMonth.parse(param); // YYYY-MM
            start = ym.atDay(1);
            end = ym.atEndOfMonth();
        } else {
            String[] range = param.split("_"); // YYYY-MM-DD_YYYY-MM-DD
            start = LocalDate.parse(range[0]);
            end = LocalDate.parse(range[1]);
        }

        List<GoalDTO> historyGoals = goalService.getGoalsByDateRange(user.getMember_id(), start, end);
        model.addAttribute("historyGoals", historyGoals);
        model.addAttribute("type", type);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "goalHistoryTable"; // AJAX로 반환
    }
   
} 