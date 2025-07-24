package com.springmvc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TalentDTO;
import com.springmvc.repository.TalentRepository;

@Service
public class TalentServiceImpl implements TalentService {

    @Autowired
    private TalentRepository talentRepository;

    @Override
    public void create(TalentDTO dto) {
        talentRepository.create(dto);
    }
    
    @Override
	public TalentDTO readone(long talentId) {		
		return talentRepository.readone(talentId);
	}

	@Override
    public List<TalentDTO> TalentByMemberId(String memberId) {
        return talentRepository.TalentByMemberId(memberId);
    }
    
    @Override
    public void update(TalentDTO dto) {
        talentRepository.update(dto);
    }

    @Override
    public void delete(int talent_id) {
        talentRepository.delete(talent_id);
    }

   

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

    @Override
    public List<TalentDTO> readTalents(int page, int size, String expert, String category, String keyword) {
        return talentRepository.readTalents(page, size, expert, category, keyword);
    }

    @Override
    public int getTalentCount(String expert, String category, String keyword) {
        return talentRepository.getTalentCount(expert, category, keyword);
    }

	@Override
	public List<TalentDTO> getTalentByAddr(String userId, String baseAddr) {
		// TODO Auto-generated method stub
		return talentRepository.findByAddrExceptUser(baseAddr, userId);
	}

	@Override
	public List<String> getAllCategories() {
		// TODO Auto-generated method stub
		return Arrays.asList("디자인", "프로그래밍", "번역", "음악", "영상편집", "글쓰기", "과외", "생활서비스", "기획창작");
	}
	@Override
	public List<TalentDTO> getTopTalentsByRequestCount(String category, int limit) {
	    return talentRepository.findTopTalentsByRequestCount(category, limit);
	}
}
