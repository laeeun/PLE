package com.springmvc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.domain.CalendarEvent;
import com.springmvc.domain.TodoDTO;
import com.springmvc.domain.TodoOccurrenceDTO;
import com.springmvc.domain.TodoCreateRequest;
import com.springmvc.enums.RecurrenceFreq;
import com.springmvc.repository.TodoOccurrenceRepository;
import com.springmvc.repository.TodoRepository;

@Service
public class TodoOccurrenceServiceImpl implements TodoOccurrenceService {

    @Autowired
    private TodoOccurrenceRepository todoOccurrenceRepository;

    @Autowired
    private TodoRepository todoRepository; // ← 상위 todo 저장/수정용으로 필요

    /* ====================================================
     * 생성
     * ==================================================== */
    @Override
    @Transactional
    public Long createTodoWithOccurrences(TodoDTO req) {
        validateCreate(req);

        // 1) 상위 todo 저장
        Long todoId = todoRepository.insertTodo(toCreateRequest(req));

        // 2) 발생건 전개 & 저장
        List<LocalDate> days = req.getFreq().expandOccurrences(
                req.getStartDate(), req.getEndDate(),
                null,  // WEEKLY: 다중 요일 지정 필요 시 Set<DayOfWeek> 전달
                null   // MONTHLY: 특정 일자 오버라이드 필요 시 전달
        );
        todoOccurrenceRepository.batchInsert(todoId, days);
        return todoId;
    }

    /* ====================================================
     * 수정 + 발생건 재생성
     * ==================================================== */
    @Override
    @Transactional
    public void updateTodoAndRegenerate(Long todoId,
                                        String title,
                                        String content,
                                        Integer priority,
                                        LocalDate deadline,
                                        RecurrenceFreq freq,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        Boolean allDay) {
        Objects.requireNonNull(todoId, "todoId");
        Objects.requireNonNull(freq, "freq");
        RecurrenceFreq.validateRange(startDate, endDate);

        // 1) 상위 todo 수정
        todoRepository.updateBasic(todoId, title, content, priority, deadline, freq, startDate, endDate, allDay);

        // 2) 기존 발생건 삭제 후 재생성
        todoOccurrenceRepository.deleteByTodoId(todoId);

        List<LocalDate> days = freq.expandOccurrences(startDate, endDate, null, null);
        todoOccurrenceRepository.batchInsert(todoId, days);
    }

    /* ====================================================
     * 달력 조회
     * ==================================================== */
    @Override
    @Transactional(readOnly = true)
    public List<CalendarEvent> getCalendarEvents(String receiverId,
                                                 LocalDate startInclusive,
                                                 LocalDate endExclusive,
                                                 boolean hideExpired) {
        List<TodoOccurrenceDTO> occs = todoOccurrenceRepository.findRangeByReceiverWithTodoInfo(
                receiverId, startInclusive, endExclusive, hideExpired
        );
        
        // 하루짜리 all-day 이벤트로 변환 (end=null 권장)
        List<CalendarEvent> events = new ArrayList<>(occs.size());
        for (TodoOccurrenceDTO o : occs) {
        	LocalDate start = o.getStartDate();
        	LocalDate end = o.getEndDate() != null ? o.getEndDate().plusDays(1) : start.plusDays(1);
            CalendarEvent ev = new CalendarEvent(
                    o.getOccurrenceId(),
                    o.getTitle(),                         // JOIN으로 title 포함 시
                    start.toString(),  // ✔️ start
                    //end.toString(),     // ✔️ exclusive end
                    o.getEndDate().toString(),
                    o.getCompleted()
            );
            events.add(ev);
        }
        
        return events;
    }

    /* ====================================================
     * 토글
     * ==================================================== */
    @Override
    @Transactional
    public void setOccurrenceCompleted(Long occurrenceId, boolean completed) {
        todoOccurrenceRepository.setCompleted(occurrenceId, completed);
    }

    @Override
    @Transactional
    public void setOccurrenceHidden(Long occurrenceId, boolean hidden) {
        todoOccurrenceRepository.setHidden(occurrenceId, hidden);
    }

    /* ====================================================
     * 단건 조회
     * ==================================================== */
    @Override
    @Transactional(readOnly = true)
    public Optional<TodoDTO> getTodo(Long todoId) {
        // TodoRepository에 DTO 반환 메서드가 없다면, Row → DTO 변환 메서드를 추가해 주세요.
        return todoRepository.findByIdAsDto(todoId);
    }

    /* ======================== 내부 유틸 ======================== */

    private void validateCreate(TodoDTO r) {
        Objects.requireNonNull(r.getWriterId(), "writerId");
        Objects.requireNonNull(r.getReceiverId(), "receiverId");
        Objects.requireNonNull(r.getTitle(), "title");
        Objects.requireNonNull(r.getFreq(), "freq");
        Objects.requireNonNull(r.getStartDate(), "startDate");
        Objects.requireNonNull(r.getEndDate(), "endDate");
        RecurrenceFreq.validateRange(r.getStartDate(), r.getEndDate());
    }

    private TodoCreateRequest toCreateRequest(TodoDTO d) {
    	TodoCreateRequest r = new TodoCreateRequest();
        r.setTradeId(d.getTradeId());
        r.setWriterId(d.getWriterId());
        r.setReceiverId(d.getReceiverId());
        r.setTitle(d.getTitle());
        r.setContent(d.getContent());
        r.setPriority(d.getPriority());
        r.setIsPersonal(d.isPersonal());
        r.setDeadline(d.getDeadline());

        r.setFreq(d.getFreq());
        r.setStartDate(d.getStartDate());
        r.setEndDate(d.getEndDate());
        r.setAllDay(d.getAllDay() == null ? Boolean.TRUE : d.getAllDay());
        return r;
    }
    
    @Override
    public boolean toggleOccurrenceCompleted(Long occurrenceId) {
        boolean current = todoOccurrenceRepository.isCompleted(occurrenceId);
        boolean newVal = !current;
        todoOccurrenceRepository.setOccurrenceCompleted(occurrenceId, newVal);
        return newVal;
    }
}
