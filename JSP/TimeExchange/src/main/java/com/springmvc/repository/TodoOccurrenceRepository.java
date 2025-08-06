package com.springmvc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springmvc.domain.TodoOccurrenceDTO;

public interface TodoOccurrenceRepository {

    // === 기본 CRUD ===
    Optional<TodoOccurrenceDTO> findById(Long occurrenceId);

    List<TodoOccurrenceDTO> findByTodoId(Long todoId);

    int deleteById(Long occurrenceId);

    int deleteByTodoId(Long todoId);

    // === 생성/배치 생성 ===
    int insertOne(Long todoId, LocalDate occurDate);

    void batchInsert(Long todoId, List<LocalDate> occurDates);

    // === 상태 토글 ===
    int setCompleted(Long occurrenceId, boolean completed);

    int setHidden(Long occurrenceId, boolean hidden);
    
    boolean isCompleted(Long occurrenceId);
    void setOccurrenceCompleted(Long occurrenceId, boolean completed);

    // === 달력 범위 조회 (조인 포함) ===
    List<TodoOccurrenceDTO> findRangeByReceiverWithTodoInfo(
            String receiverId,
            LocalDate startInclusive,
            LocalDate endExclusive,
            boolean hideExpired
    );

    // === 유틸 ===
    int countByTodoId(Long todoId);

    int countExpiredTodosByReceiver(String receiverId);
    
    List<Map<String, Object>> getOccurrenceStatsByDateRange(String receiverId, LocalDate start, LocalDate end);
}
