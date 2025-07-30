package com.springmvc.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.domain.Member;
import com.springmvc.repository.TodoRepository;
import com.springmvc.service.TodoHistoryService;

@RestController
@RequestMapping("/todo/stats")
public class TodoStatsController {

    private final TodoHistoryService todoHistoryService;
    private final TodoRepository todoRepository;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    
    @Autowired
    public TodoStatsController(TodoHistoryService todoHistoryService, TodoRepository todoRepository) {
        this.todoHistoryService = todoHistoryService;
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getStats(@RequestParam String receiverId) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 1. 오늘 완료율(todo 테이블에서 실시간 조회)
        Map<String, Object> today = todoRepository.getTodayStats(receiverId);
        today.put("date", LocalDate.now().toString());
        result.add(today);

        // 2. 과거 6일 완료율(todo_history 조회)
        result.addAll(todoHistoryService.findStatsByReceiverId(receiverId));
        return result;
    }
    
    @GetMapping("/today")
    public Map<String, Object> getTodayStats(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            // 비로그인 시 0으로 응답(또는 401로 바꿔도 됨)
            return Map.of("date", java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul")).toString(),
                          "total", 0, "completed", 0, "rate", 0);
        }
        String memberId = user.getMember_id();

        // DB: 오늘(KST가 아니라 DB 타임존 기준) — 필요시 타임존 맞추기
        Map<String, Object> raw = todoRepository.getTodayStats(memberId);
        // COALESCE가 쿼리에 들어 있어도 방어적으로 0 치환
        int total = ((Number) raw.getOrDefault("total", 0)).intValue();
        int completed = ((Number) raw.getOrDefault("completed", 0)).intValue();
        int rate = (total > 0) ? (int) Math.round((completed * 100.0) / total) : 0;

        return Map.of(
            "date", java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul")).toString(),
            "total", total,
            "completed", completed,
            "rate", rate
        );
    }
    
    @GetMapping("/today/by-type")
    public Map<String, Object> getTodayStatsByType(HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        var todayStr = java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul")).toString();

        if (user == null) {
            return Map.of(
               "date", todayStr,
               "all",      Map.of("total", 0, "completed", 0, "rate", 0),
               "assigned", Map.of("total", 0, "completed", 0, "rate", 0),
               "created",  Map.of("total", 0, "completed", 0, "rate", 0)
            );
        }

        String memberId = user.getMember_id();

        Map<String, Object> allRaw      = todoRepository.getTodayStatsAll(memberId);
        Map<String, Object> assignedRaw = todoRepository.getTodayStatsAssigned(memberId);
        Map<String, Object> createdRaw  = todoRepository.getTodayStatsCreated(memberId);

        // 안전 변환 + rate 계산
        java.util.function.Function<Map<String, Object>, Map<String, Object>> normalize = (raw) -> {
            int total = ((Number) raw.getOrDefault("total", 0)).intValue();
            int completed = ((Number) raw.getOrDefault("completed", 0)).intValue();
            int rate = total > 0 ? (int)Math.round(completed * 100.0 / total) : 0;
            return Map.of("total", total, "completed", completed, "rate", rate);
        };

        return Map.of(
            "date", todayStr,
            "all",      normalize.apply(allRaw),
            "assigned", normalize.apply(assignedRaw),
            "created",  normalize.apply(createdRaw)
        );
    }
    
    
    @GetMapping("/calendar")
    public Map<String, Object> calendar(int year, int month, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            return Map.of("year", year, "month", month, "days", Collections.emptyList());
        }

        String receiverId = user.getMember_id();
        java.time.YearMonth ym = java.time.YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end   = ym.atEndOfMonth();
        LocalDate today = LocalDate.now(KST);

        // 1) history에서 월 범위 집계 (과거 전부 + 오늘 포함)
        List<Map<String, Object>> raw = todoHistoryService.findStatsByDateRange(receiverId, start, end);
        Map<String, Map<String, Object>> histMap = new HashMap<>();
        for (Map<String, Object> r : raw) {
            histMap.put(String.valueOf(r.get("date")), r);
        }

        // 2) 오늘은 live로 대체
        Map<String, Object> todayLive = null;
        if (java.time.YearMonth.from(today).equals(ym)) {
            todayLive = todoRepository.getTodayStatsAll(receiverId); // {total, completed}
        }

        // 3) days 조립
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
            } // 미래는 0/0

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
    
    private int toInt(Object o) {
        if (o == null) return 0;
        if (o instanceof Number) return ((Number) o).intValue();
        try { return Integer.parseInt(String.valueOf(o)); } catch (Exception e) { return 0; }
    }
}
