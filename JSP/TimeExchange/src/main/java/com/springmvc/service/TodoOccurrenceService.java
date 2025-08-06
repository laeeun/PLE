package com.springmvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.springmvc.domain.CalendarEvent;
import com.springmvc.domain.TodoDTO;
import com.springmvc.enums.RecurrenceFreq;

public interface TodoOccurrenceService {

    /** 상위 todo 생성 + 발생건 배치 생성 */
    Long createTodoWithOccurrences(TodoDTO req);

    /** 상위 todo 수정 + 발생건 재생성 */
    void updateTodoAndRegenerate(Long todoId,
                                 String title,
                                 String content,
                                 Integer priority,
                                 LocalDate deadline,
                                 RecurrenceFreq freq,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 Boolean allDay);

    /** 달력 범위 조회(FullCalendar start~end(배타) 관례) */
    List<CalendarEvent> getCalendarEvents(String receiverId,
                                          LocalDate startInclusive,
                                          LocalDate endExclusive,
                                          boolean hideExpired);

    /** 발생건 완료 토글 */
    void setOccurrenceCompleted(Long occurrenceId, boolean completed);

    /** 발생건 숨김 토글 */
    void setOccurrenceHidden(Long occurrenceId, boolean hidden);

    /** 상위 todo 단건 조회 */
    Optional<TodoDTO> getTodo(Long todoId);
    boolean toggleOccurrenceCompleted(Long occurrenceId);
}
