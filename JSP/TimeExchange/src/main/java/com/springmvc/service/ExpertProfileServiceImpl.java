package com.springmvc.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ExpertProfileDTO;
import com.springmvc.repository.ExpertProfileRepository;

@Service
public class ExpertProfileServiceImpl implements ExpertProfileService {

    @Autowired
    private ExpertProfileRepository expertRepository;

    @Override
    public void save(ExpertProfileDTO expert) {
    	expertRepository.save(expert);
    }

    @Override
    public ExpertProfileDTO findByMemberId(String memberId) {
        return expertRepository.findByMemberId(memberId);
    }

    @Override
    public List<ExpertProfileDTO> findAll() {
        return expertRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        expertRepository.deleteById(id);
    }

	@Override
	public void update(ExpertProfileDTO expert) {
		expertRepository.update(expert);
	}
    
	@Override
    public boolean existsByMemberId(String memberId) {
        return expertRepository.countByMemberId(memberId) > 0;
    }
}
