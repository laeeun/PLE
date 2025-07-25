package com.springmvc.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springmvc.domain.Member;
import com.springmvc.domain.TodoDTO;
import com.springmvc.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    // ✅ [개인용] 할일 추가
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addTodo(@RequestBody Map<String, String> body,
                                       HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        String memberId = user.getMember_id();

        TodoDTO todo = new TodoDTO();
        todo.setWriterId(memberId);
        todo.setReceiverId(memberId);
        todo.setPersonal(true);
        todo.setTitle(body.get("title"));
        todo.setContent(body.get("content"));
        todo.setCompleted(false);
        todo.setPriority(2);
        todo.setCreated_at(LocalDateTime.now());
        todo.setUpdated_at(LocalDateTime.now());

        todoService.createTODO(todo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
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

    // ✅ 필터 (AJAX) - 타입 + 완료 여부 동시 필터
    @GetMapping("/filter")
    @ResponseBody
    public List<TodoDTO> filterTodos(@RequestParam(required = false) String type,
                                     @RequestParam(required = false) String completed,
                                     HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return null;

        String memberId = user.getMember_id();
        Boolean isCompleted = (completed == null || completed.isEmpty()) ? null : Boolean.parseBoolean(completed);

        if ("created".equals(type)) {
            return todoService.findByWriterId(memberId, isCompleted); // 내가 만든 할일
        } else if ("received".equals(type)) {
            return todoService.findAssignedTodos(memberId, isCompleted); // 받은 숙제
        } else {
            return todoService.findAllTodos(memberId, isCompleted); // 전체
        }
    }

    // ✅ 완료 상태 토글
    @PostMapping("/complete")
    @ResponseBody
    public Map<String, Object> updateTodo(@RequestParam("todoId") long todoId) {
        TodoDTO todo = todoService.findById(todoId);
        boolean newCompleted = !todo.isCompleted();
        todoService.updateCompleted(todoId, newCompleted);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("completed", newCompleted);
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
        Member user = (Member) session.getAttribute("loggedInUser");
        String memberId = user.getMember_id();

        TodoDTO todo = new TodoDTO();
        todo.setTodoId(Long.parseLong(body.get("todoId").toString()));
        todo.setTitle((String) body.get("title"));
        todo.setContent((String) body.get("content"));
        todo.setWriterId(memberId);
        todo.setReceiverId(memberId);
        todo.setPersonal(true);
        todo.setUpdated_at(LocalDateTime.now());

        todoService.updateTODO(todo);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }



    // ✅ 특정 거래(trade_id) 관련 숙제 목록 조회
    @GetMapping("/trade")
    @ResponseBody
    public List<TodoDTO> getTodosByTradeId(@RequestParam("tradeId") Long tradeId) {
        return todoService.findByTradeId(tradeId);
    }
}
