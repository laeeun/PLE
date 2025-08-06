package com.springmvc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.CalendarEvent;
import com.springmvc.domain.Member;
import com.springmvc.domain.TodoDTO;
import com.springmvc.enums.RecurrenceFreq;
import com.springmvc.service.TodoOccurrenceService;
import com.springmvc.service.TodoService;
import com.springmvc.service.NotificationService;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;
    @Autowired
    TodoOccurrenceService todoOccurrenceService;
    @Autowired
    NotificationService notificationService;
    // ✅ [개인용] 할일 추가
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addTodo(@RequestBody Map<String, String> body, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        Map<String, Object> result = new HashMap<>();

        // ✅ 로그인 여부 확인
        if (user == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다.");
            return result;
        }

        String memberId = user.getMember_id();
        TodoDTO todo = new TodoDTO();

        // ✅ 기본 정보 세팅
        todo.setWriterId(memberId);
        todo.setReceiverId(memberId);
        todo.setPersonal(true);
        todo.setTitle(body.get("title"));
        todo.setContent(body.get("content"));
        todo.setCompleted(false);
        todo.setPriority(2);
        todo.setCreated_at(LocalDateTime.now());
        todo.setUpdated_at(LocalDateTime.now());

        // ✅ 날짜 파싱 (빈 문자열, null 대비)
        String startStr = body.get("startDate");
        String endStr   = body.get("endDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = (startStr != null && !startStr.isBlank()) ? LocalDate.parse(startStr, formatter) : LocalDate.now();
        } catch (Exception e) {
            startDate = LocalDate.now();
        }

        try {
            endDate = (endStr != null && !endStr.isBlank()) ? LocalDate.parse(endStr, formatter) : startDate;
        } catch (Exception e) {
            endDate = startDate;
        }

        todo.setStartDate(startDate);
        todo.setEndDate(endDate);
        todo.setStartDateStr(startStr);
        todo.setEndDateStr(endStr);
        String freqStr = body.get("freq");
        RecurrenceFreq freq;
        try {
            freq = RecurrenceFreq.valueOf(freqStr != null ? freqStr : "NONE");
        } catch (IllegalArgumentException e) {
            freq = RecurrenceFreq.NONE;
        }
        todo.setFreq(freq);
        todo.setAllDay(true); // 기본값

        // ✅ DB 저장
        try {
            todoService.createTODO(todo);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "TODO 저장 중 오류: " + e.getMessage());
        }

        return result;
    }


    // ✅ [숙제용] 다른 사람에게 숙제 할당
    @PostMapping("/assign")
    @ResponseBody
    public Map<String, Object> assignTodo(@RequestBody TodoDTO todo,
                                          HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        String writerId = user.getMember_id();

        todo.setWriterId(writerId);
        todo.setPersonal(false);
        todo.setCompleted(false);
        todo.setCreated_at(LocalDateTime.now());
        todo.setUpdated_at(LocalDateTime.now());

        todoService.createTODO(todo);

        // ✅ 알림 생성
        String senderUsername = user.getUserName();
        String receiverUsername = todo.getReceiverId(); // receiverId가 username이라고 가정
        String content = String.format("새로운 숙제가 할당되었습니다: %s", todo.getTitle());
        
        notificationService.createSimpleNotification(
            senderUsername, 
            receiverUsername, 
            "숙제", 
            content, 
            todo.getTodoId(), 
            "todo"
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    // ✅ 전체 목록 조회 (내가 받은 할일 = 개인 + 숙제)
    @GetMapping({"", "/"})
    public String getTodoList(HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<TodoDTO> todolist = todoService.findByReceiverId(user.getMember_id());
        model.addAttribute("todolist", todolist);
        return "myTodo";
    }

    // ✅ 내가 받은 숙제 목록만 조회
    @GetMapping("/assigned")
    public String getAssignedTodos(HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<TodoDTO> assignedList = todoService.findAssignedTodos(user.getMember_id());
        model.addAttribute("todolist", assignedList);
        return "myTodo";
    }

    // ✅ 내가 만든 할일 목록 조회
    @GetMapping("/created")
    public String getCreatedTodos(HttpSession session, Model model) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<TodoDTO> createdList = todoService.findByWriterId(user.getMember_id());
        model.addAttribute("todolist", createdList);
        return "myTodo";
    }

    // ✅ 필터 (AJAX) - 타입 + 완료 여부 + 반복 주기 동시 필터
    @GetMapping("/filter")
    @ResponseBody
    public List<TodoDTO> filterTodos(@RequestParam(required = false) String type,
                                     @RequestParam(required = false) String completed,
                                     @RequestParam(required = false) String freq,
                                     @RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return null;

        String memberId = user.getMember_id();
        Boolean isCompleted = (completed == null || completed.isEmpty()) ? null : Boolean.parseBoolean(completed);
        LocalDate target = (date != null) ? date : LocalDate.now(ZoneId.of("Asia/Seoul"));

        if ("created".equals(type)) {
            return todoService.findByWriterId(memberId, isCompleted, freq); // 내가 만든 할일
        } else if ("received".equals(type)) {
            return todoService.findAssignedTodos(memberId, isCompleted, freq); // 받은 숙제
        } else {
            return todoService.findAllTodos(memberId, isCompleted, freq); // 전체
        }
    }

    // ✅ 완료 상태 토글
    @PostMapping("/complete")
    @ResponseBody
    public Map<String, Object> updateTodo(
            @RequestParam("todoId") long todoId,
            @RequestParam(value = "isOccurrence", defaultValue = "false") boolean isOccurrence) {

        Map<String, Object> result = new HashMap<>();

        try {
            if (isOccurrence) {
                // 반복 일정 처리
                boolean completed = todoOccurrenceService.toggleOccurrenceCompleted(todoId); // ✅ toggle 기능 추가
                result.put("completed", completed);
            } else {
                // 일반 todo 처리
                TodoDTO todo = todoService.findById(todoId);
                boolean newCompleted = !todo.isCompleted();
                todoService.updateCompleted(todoId, newCompleted);
                result.put("completed", newCompleted);
            }

            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }
    // ✅ 할일 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteTodo(@RequestParam("todoId") long todoId) {
        Map<String, Object> result = new HashMap<>();
        try {
            todoService.deleteById(todoId);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    // ✅ 제목/내용 수정
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateTodoContent(@RequestBody Map<String, Object> body,
                                                 HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다.");
            return result;
        }

        // 입력값 파싱 & 검증
        Object idRaw = body.get("todoId");
        String title   = body.get("title")   != null ? body.get("title").toString().trim()   : null;
        String content = body.get("content") != null ? body.get("content").toString().trim() : null;

        if (idRaw == null || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            result.put("success", false);
            result.put("error", "todoId, title, content는 필수입니다.");
            return result;
        }

        long todoId;
        try {
            todoId = Long.parseLong(idRaw.toString());
        } catch (NumberFormatException e) {
            result.put("success", false);
            result.put("error", "todoId 형식이 올바르지 않습니다.");
            return result;
        }

        // 작성자/수신자 본인만 수정하도록 검증하려면 ownerId 사용
        String ownerId = user.getMember_id();

        int changed = todoService.updateTitleContent(todoId, title, content, ownerId);
        if (changed <= 0) {
            result.put("success", false);
            result.put("error", "수정 대상이 없거나 권한이 없습니다.");
            return result;
        }

        result.put("success", true);
        return result;
    }
    
    // ✅ 특정 거래(trade_id) 관련 숙제 목록 조회
    @GetMapping("/trade")
    @ResponseBody
    public List<TodoDTO> getTodosByTradeId(@RequestParam("tradeId") Long tradeId) {
        return todoService.findByTradeId(tradeId);
    }
    
    @PostMapping("/recurring")
    @ResponseBody
    public Map<String, Object> createRecurringTodo(@RequestBody TodoDTO dto, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        Map<String, Object> result = new HashMap<>();

        if (user == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다.");
            return result;
        }

        dto.setWriterId(user.getMember_id());
        dto.setReceiverId(user.getMember_id()); // 개인용으로 가정
        dto.setPersonal(true);
        dto.setCompleted(false);

        try {
            todoOccurrenceService.createTodoWithOccurrences(dto);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }
    

    @GetMapping("/events")
    @ResponseBody
    public List<CalendarEvent> getCalendarEvents(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            HttpSession session) {

        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return List.of();

        return todoOccurrenceService.getCalendarEvents(user.getMember_id(), start, end, true);
    }
    
    
    
    @PostMapping("/occurrence/complete")
    @ResponseBody
    public Map<String, Object> completeOccurrence(@RequestParam("occurrenceId") Long id) {
        todoOccurrenceService.setOccurrenceCompleted(id, true);
        return Map.of("success", true);
    }

    @PostMapping("/occurrence/hide")
    @ResponseBody
    public Map<String, Object> hideOccurrence(@RequestParam("occurrenceId") Long id) {
        todoOccurrenceService.setOccurrenceHidden(id, true);
        return Map.of("success", true);
    }
    
    
}
