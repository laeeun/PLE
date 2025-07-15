package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ExpertDTO;
import com.springmvc.repository.ExpertReposiory;

@Service
public class ExpertServiceImpl implements ExpertService{
	@Autowired
	ExpertReposiory expertReposiory;
	
	// 생성(Create)
	@Override
	public void create(ExpertDTO dto) {
		expertReposiory.create(dto);
	}
	// 단건 조회(readOne)
	@Override
	public ExpertDTO readOne(Long expert_board_id) {	
		return expertReposiory.readOne(expert_board_id);
	}
	// 업데이트(update)
	@Override
	public void update(ExpertDTO dto) {
		expertReposiory.update(dto);
	}
	// 게시글 삭제(delete)
	@Override
	public void delete(Long expert_board_id) {
		expertReposiory.delete(expert_board_id);
		
	}
	// 전체 목록 조회 (페이징 처리)
	@Override
	public List<ExpertDTO> readPaged(int offset, int size) {		
		return expertReposiory.readPaged(offset, size);
	}
	// 전체 재능 개수 조회 (페이징용)
	@Override
	public int countAll() {		
		return expertReposiory.countAll();
	}
	// 카테고리별 목록 조회 (페이징 처리)
	@Override
	public List<ExpertDTO> readPagedCategory(String category, int page, int size) {		
		return expertReposiory.readPagedCategory(category, page, size);
	}
	// 카테고리별 게시물 수 (페이징 계산용)
	@Override
	public int getCountByCategory(String category) {		
		return expertReposiory.getCountByCategory(category);
	}
	// 검색 결과 목록 조회 (페이징 처리)
	@Override
	public List<ExpertDTO> searchPagedExpert(String keyword, int page, int size) {		
		return expertReposiory.searchPagedExpert(keyword, page, size);
	}
	 // 검색 결과 수 (페이징 계산용)
	@Override
	public int countSearchResult(String keyword) {
		// TODO Auto-generated method stub
		return expertReposiory.countSearchResult(keyword);
	}
	//시간 포맷 변환
	@Override
	public void formatAvailableTime(ExpertDTO dto) {
		 String raw = dto.getAvailable_time();
		    if (raw == null || raw.isEmpty()) return;
		    // 예: "월 14:00,화 15:00"
		    String[] slots = raw.split(",");
		    List<String> formatted = new ArrayList<>();

		    for (String slot : slots) {
		        String[] parts = slot.trim().split(" ");
		        if (parts.length == 2) {
		            String dayKor = convertDay(parts[0]); // 요일 변환
		            String time = formatTime(parts[1]);   // 시간 변환
		            formatted.add(dayKor + " " + time);
		        }
		    }

		    dto.setAvailable_time(String.join(", ", formatted));
		
	}
	
	private String convertDay(String kor) {
	    switch (kor) {
	        case "월": return "월요일";
	        case "화": return "화요일";
	        case "수": return "수요일";
	        case "목": return "목요일";
	        case "금": return "금요일";
	        case "토": return "토요일";
	        case "일": return "일요일";
	        default: return kor;
	    }
	}

	private String formatTime(String time) {
	    try {
	        String[] parts = time.split(":");
	        int hour = Integer.parseInt(parts[0]);
	        String min = parts[1];
	        String period = hour < 12 ? "오전" : "오후";
	        hour = (hour == 0 || hour == 12) ? 12 : hour % 12;
	        return period + " " + hour + ":" + min;
	    } catch (Exception e) {
	        return time; // 실패 시 원본 유지
	    }
	}
	
}
