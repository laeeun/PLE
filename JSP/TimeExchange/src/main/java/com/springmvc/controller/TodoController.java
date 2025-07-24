package com.springmvc.controller;

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

import com.springmvc.domain.Member;
import com.springmvc.domain.TodoDTO;
import com.springmvc.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;
    
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addTodo(@RequestBody Map<String, String> body,
                                       HttpSession session) {
        
    	Member user = (Member) session.getAttribute("loggedInUser");
    	
        TodoDTO todo = new TodoDTO();
        todo.setMemberId(user.getMember_id());
        todo.setTitle(body.get("title"));
        todo.setContent(body.get("content"));
        todo.setCompleted(false);
        todo.setPriority(2); // 기본값 중간 우선순위
        System.out.println(todo);
        todoService.createTODO(todo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
    
    // 전체 목록
    @GetMapping
    public String getTodoList(HttpSession session, Model model) {
        System.out.println("투두리스트 입장");
    	Member user = (Member) session.getAttribute("loggedInUser");
        
    	if (user == null) {
            return "redirect:/login";
        }

        List<TodoDTO> todolist = todoService.readAllTODO(user.getMember_id());
        model.addAttribute("todolist", todolist);
        return "myTodo";
    }

    // 완료/미완료 필터 AJAX 요청 처리
    @GetMapping("/filter")
    @ResponseBody
    public List<TodoDTO> filterByCompleted(@RequestParam(required = false) String completed,
                                           HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser");
        if (user == null) return null;
        
        if (completed == null || completed.equals("")) {
        	
            return todoService.readAllTODO(user.getMember_id());
        } else {
            boolean isCompleted = Boolean.parseBoolean(completed);
            return todoService.readTODOByCompleted(user.getMember_id(), isCompleted);
        }
    }
    
    @PostMapping("/complete")
    @ResponseBody
    public Map<String, Object> updateTodo(@RequestParam("todoId") long todoId) {
        TodoDTO todo = todoService.readoneTODO(todoId);
        boolean newCompleted = !todo.isCompleted();
        todoService.updateCompleted(todoId, newCompleted);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("completed", newCompleted);
        return result;
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteTodo(@RequestParam("todoId") long todoId){
        Map<String, Object> result = new HashMap<>();
        System.out.println(todoId);
        try {
            todoService.deleteTODO(todoId);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateTodoContent(@RequestBody Map<String, Object> body,HttpSession session) {
        System.out.println("body map: " + body);
        Member user = (Member) session.getAttribute("loggedInUser");
        // 직접 DTO로 변환해보기
        
        TodoDTO todo = new TodoDTO();
        todo.setTodoId(Long.valueOf(body.get("todoId").toString()));
        todo.setTitle((String) body.get("title"));
        todo.setContent((String) body.get("content"));
        todo.setMemberId(user.getMember_id());
        System.out.println(todo);
        
        todoService.updateTODO(todo);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    } 
}
