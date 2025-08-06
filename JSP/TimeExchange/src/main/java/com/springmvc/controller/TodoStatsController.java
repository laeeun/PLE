package com.springmvc.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springmvc.domain.Member;
import com.springmvc.repository.TodoOccurrenceRepository;
import com.springmvc.repository.TodoRepository;
import com.springmvc.service.TodoHistoryService;
import com.springmvc.service.GoalService;

@RestController
@RequestMapping("/todo/stats")
public class TodoStatsController {

    private final TodoHistoryService todoHistoryService;
    private final TodoRepository todoRepository;
    private final TodoOccurrenceRepository todoOccurrenceRepository;
    private final GoalService goalService;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    @Autowired
    public TodoStatsController(TodoHistoryService todoHistoryService,
                               TodoRepository todoRepository,
                               TodoOccurrenceRepository todoOccurrenceRepository,
                               GoalService goalService) {
        this.todoHistoryService = todoHistoryService;
        this.todoRepository = todoRepository;
        this.todoOccurrenceRepository = todoOccurrenceRepository;
        this.goalService = goalService;
    }

    @GetMapping
    public List<Map<String, Object>> getStats(@RequestParam String receiverId) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> today = todoRepository.getTodayStats(receiverId);
        today.put("date", LocalDate.now(KST).toString());
        result.add(today);

        result.addAll(todoHistoryService.findStatsByReceiverId(receiverId));
        return result;
    }

    @GetMapping("/today")
    public Map<String, Object> getTodayStats(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("date", LocalDate.now(KST).toString(),
                          "total", 0, "completed", 0, "rate", 0);
        }

        String memberId = user.getMember_id();
        Map<String, Object> raw = todoRepository.getTodayStats(memberId);
        int total = toInt(raw.get("total"));
        int completed = toInt(raw.get("completed"));
        int rate = (total > 0) ? (int) Math.round(completed * 100.0 / total) : 0;

        return Map.of(
            "date", LocalDate.now(KST).toString(),
            "total", total,
            "completed", completed,
            "rate", rate
        );
    }

    @GetMapping("/today/by-type")
    public Map<String, Object> getTodayStatsByType(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        var todayStr = LocalDate.now(KST).toString();

        if (user == null) {
            return Map.of(
               "date", todayStr,
               "all",      emptyStat(),
               "assigned", emptyStat(),
               "created",  emptyStat(),
               "priority", Map.of(
                   "high", emptyStat(),
                   "medium", emptyStat(),
                   "low", emptyStat()
               ),
               "timeframe", Map.of(
                   "morning", emptyStat(),
                   "afternoon", emptyStat(),
                   "evening", emptyStat()
               )
            );
        }

        String memberId = user.getMember_id();
        Map<String, Object> all      = todoRepository.getTodayStatsAll(memberId);
        Map<String, Object> assigned = todoRepository.getTodayStatsAssigned(memberId);
        Map<String, Object> created  = todoRepository.getTodayStatsCreated(memberId);
        Map<String, Object> priority = todoRepository.getTodayStatsByPriority(memberId);
        
        // 목표 진행률 조회
        Map<String, Object> monthlyGoals = goalService.getMonthlyGoalProgress(user.getMember_id(), 
            LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        Map<String, Object> weeklyGoals = goalService.getWeeklyGoalProgress(user.getMember_id(), 
            LocalDate.now().with(java.time.DayOfWeek.MONDAY));

        return Map.of(
            "date", todayStr,
            "all", normalize(all),
            "assigned", normalize(assigned),
            "created", normalize(created),
            "priority", priority,
            "goals", Map.of(
                "monthly", normalize(monthlyGoals),
                "weekly", normalize(weeklyGoals)
            )
        );
    }

    @GetMapping("/calendar")
    public Map<String, Object> calendar(int year, int month, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("year", year, "month", month, "days", Collections.emptyList());
        }

        String receiverId = user.getMember_id();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        LocalDate today = LocalDate.now(KST);

        List<Map<String, Object>> raw = todoHistoryService.findStatsByDateRange(receiverId, start, end);
        Map<String, Map<String, Object>> histMap = new HashMap<>();
        for (Map<String, Object> r : raw) {
            histMap.put(String.valueOf(r.get("date")), r);
        }

        Map<String, Object> todayLive = null;
        if (LocalDate.now(KST).getMonthValue() == month && LocalDate.now(KST).getYear() == year) {
            todayLive = todoRepository.getTodayStatsAll(receiverId);
        }

        List<Map<String, Object>> days = new ArrayList<>();
        for (LocalDate cur = start; !cur.isAfter(end); cur = cur.plusDays(1)) {
            String key = cur.toString();
            int total = 0, comp = 0;

            if (cur.isBefore(today)) {
                Map<String, Object> h = histMap.get(key);
                if (h != null) {
                    total = toInt(h.get("total"));
                    comp  = toInt(h.get("completed"));
                }
            } else if (cur.isEqual(today)) {
                if (todayLive != null) {
                    total = toInt(todayLive.get("total"));
                    comp  = toInt(todayLive.get("completed"));
                }
            }

            int rate = (total > 0) ? (int) Math.round(comp * 100.0 / total) : 0;
            days.add(Map.of(
                "date", key,
                "total", total,
                "completed", comp,
                "rate", rate,
                "isFuture", cur.isAfter(today)
            ));
        }

        return Map.of("year", year, "month", month, "days", days);
    }

    // ✅ [추가] 반복 발생건 통계
    @GetMapping("/recurring")
    public Map<String, Object> recurringStats(
            @RequestParam int year,
            @RequestParam int month,
            HttpSession session) {

        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("year", year, "month", month, "days", Collections.emptyList());
        }

        String receiverId = user.getMember_id();
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.withDayOfMonth(start.lengthOfMonth());

        List<Map<String, Object>> raw = todoOccurrenceRepository.getOccurrenceStatsByDateRange(receiverId, start, end);
        List<Map<String, Object>> days = new ArrayList<>();

        for (Map<String, Object> r : raw) {
            int total = toInt(r.get("total"));
            int comp  = toInt(r.get("completed"));
            int rate  = (total > 0) ? (int) Math.round(comp * 100.0 / total) : 0;

            days.add(Map.of(
                "date", r.get("occur_date"),
                "total", total,
                "completed", comp,
                "rate", rate
            ));
        }

        return Map.of("year", year, "month", month, "days", days);
    }

    // =================== 유틸 =====================

    private int toInt(Object o) {
        if (o == null) return 0;
        if (o instanceof Number) return ((Number) o).intValue();
        try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return 0; }
    }

    private Map<String, Object> normalize(Map<String, Object> raw) {
        int total = toInt(raw.get("total"));
        int completed = toInt(raw.get("completed"));
        int rate = (total > 0) ? (int) Math.round(completed * 100.0 / total) : 0;
        return Map.of("total", total, "completed", completed, "rate", rate);
    }

    private Map<String, Object> emptyStat() {
        return Map.of("total", 0, "completed", 0, "rate", 0);
    }
    
    // ✅ 백업 상태 확인 엔드포인트
    @GetMapping("/backup/status")
    public Map<String, Object> getBackupStatus(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }
        
        LocalDate yesterday = LocalDate.now().minusDays(1);
        int missedBackups = todoRepository.countMissedBackups(yesterday);
        
        return Map.of(
            "success", true,
            "missedBackups", missedBackups,
            "hasMissedBackups", missedBackups > 0,
            "lastBackupDate", yesterday.toString()
        );
    }
}
