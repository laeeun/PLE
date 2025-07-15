package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TalentDTO;
import com.springmvc.repository.TalentRepository;

@Service
public class TalentServiceImpl implements TalentService {

	@Autowired
	private TalentRepository talentRepository;
	
	// 재능 등록
	@Override
	public void create(TalentDTO dto) {
		talentRepository.create(dto);
	}

	// ID로 재능 하나 조회
	@Override
	public TalentDTO readone(long id) {
		TalentDTO dto = null;
		dto = talentRepository.readone(id);
		return dto;
	}

	// 전체 재능 목록 조회
	@Override
	public List<TalentDTO> readAll() {
		List<TalentDTO> dtoList = new ArrayList<TalentDTO>();
		dtoList = talentRepository.readAll();
		return dtoList;
	}

	// 재능 정보 수정
	@Override
	public void update(TalentDTO dto) {
		talentRepository.update(dto);
	}

	// 재능 삭제
	@Override
	public void delete(int id) {
		talentRepository.delete(id);
	}
	//멤버 아이디로 재능조회
	@Override
	public List<TalentDTO> TalentByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return talentRepository.TalentByMemberId(memberId);
	}

	// 특정 카테고리의 재능 목록 조회
	@Override
	public List<TalentDTO> TalentByCategory(String category) {
		List<TalentDTO> dtoList = new ArrayList<TalentDTO>();
		dtoList = talentRepository.TalentByCategory(category);
		return dtoList;
	}

	// 키워드로 검색된 재능 목록 조회
	@Override
	public List<TalentDTO> searchTalent(String keyword) {
		List<TalentDTO> dtoList = new ArrayList<TalentDTO>();
		dtoList = talentRepository.searchTalent(keyword);
		return dtoList;
	}

	// 페이징된 전체 재능 목록 조회
	@Override
	public List<TalentDTO> readPagedList(int page, int size) {
		int offset = (page - 1) * size;
		return talentRepository.readPaged(offset, size);
	}

	// 전체 재능 개수 조회 (페이징용)
	@Override
	public int getCountAll() {
		return talentRepository.countAll();
	}

	// 페이징된 특정 카테고리 재능 목록 조회
	@Override
	public List<TalentDTO> readPagedCategory(String category, int page, int size) {
		return talentRepository.readPagedCategory(category, page, size);
	}

	// 특정 카테고리의 총 개수 반환
	@Override
	public int getCountByCategory(String category) {
		return talentRepository.getCountByCategory(category);
	}

	// 페이징된 검색 결과 목록 조회
	@Override
	public List<TalentDTO> searchPagedTalent(String keyword, int page, int size) {
		return talentRepository.searchPagedTalent(keyword, page, size);
	}

	// 키워드 검색 결과 총 개수 반환
	@Override
	public int countSearchResult(String keyword) {
		return talentRepository.countSearchResult(keyword);
	}
	//시간 변환 로직
	@Override
	public void formatTimeSlot(TalentDTO dto) {
		int total = dto.getTimeSlot();
	    if (total >= 60) {
	        int hours = total / 60;
	        int minutes = total % 60;
	        String result = hours + "시간";
	        if (minutes > 0) {
	            result += " " + minutes + "분";
	        }
	        dto.setTimeSlotDisplay(result);
	    } else {
	        dto.setTimeSlotDisplay(total + "분");
	    }
	}	
}
