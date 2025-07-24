package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.TodoDTO;

public interface TodoRepository {
	//todo 생성
	void createTODO(TodoDTO todo);
	//todo 전체 리스트 출력 >> 중요도 기준 정렬
	List<TodoDTO> readAllTODO(String memberId);
	//todo 업데이트
	void updateTODO(TodoDTO todo);
	//todo 삭제
	void deleteTODO(long TodoId);
	//todo 하나보기
	TodoDTO readoneTODO(long TodoId);
	//완료 여부로 필터링
	List<TodoDTO> readTODOByCompleted(String memberId, boolean completed);
	//완료 상태만 빠르게 업데이트하는 전용 메서드 (AJAX용)
	void updateCompleted(long todoId, boolean completed);
}
