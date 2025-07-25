package com.springmvc.service;

import com.springmvc.domain.ExpertProfileDTO;
import com.springmvc.repository.ExpertProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertProfileServiceImpl implements ExpertProfileService {

    @Autowired
    private ExpertProfileRepository expertRepo;

    @Override
    public void save(ExpertProfileDTO expert) {
        expertRepo.save(expert);
    }

    @Override
    public Optional<ExpertProfileDTO> findByMemberId(String memberId) {
        return expertRepo.findByMemberId(memberId);
    }

    @Override
    public List<ExpertProfileDTO> findAll() {
        return expertRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        expertRepo.deleteById(id);
    }
}
