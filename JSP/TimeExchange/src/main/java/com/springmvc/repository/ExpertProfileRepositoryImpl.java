package com.springmvc.repository;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.domain.ExpertProfileDTO;

@Repository
public class ExpertProfileRepositoryImpl implements ExpertProfileRepository {

    @Autowired
    private JdbcTemplate template;

    private final RowMapper rowMapper = new ExpertProfileRowMapper();

    // ✅ 저장 (INSERT)
    @Override
    public void save(ExpertProfileDTO expert) {
        String sql = "INSERT INTO expert_profile (member_id, career, university, certification, introduction, file_names, submitted_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String fileNamesStr = (expert.getFileNames() != null)
                ? String.join(",", expert.getFileNames()) : null;

        template.update(sql,
                expert.getMemberId(),
                expert.getCareer(),
                expert.getUniversity(),
                expert.getCertification(),
                expert.getIntroduction(),  
                fileNamesStr,
                Timestamp.valueOf(expert.getSubmittedAt())
        );
    }
    
    

    // ✅ member_id로 1건 조회
    @Override
    public ExpertProfileDTO findByMemberId(String memberId) {
        String sql = "SELECT * FROM expert_profile WHERE member_id = ?";
        List<ExpertProfileDTO> expert = template.query(sql, new Object[]{memberId}, new ExpertProfileRowMapper());

        return expert.isEmpty() ? null : expert.get(0); // 결과가 없으면 null
    }

    // ✅ 전체 조회
    @Override
    public List<ExpertProfileDTO> findAll() {
        String sql = "SELECT * FROM expert_profile ORDER BY submitted_at DESC";
        return template.query(sql, rowMapper);
    }

    // ❌ 승인 관련 메서드는 이제 필요 없음 → 삭제함

    // ✅ 삭제
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM expert_profile WHERE id = ?";
        template.update(sql, id);
    }



    @Override
    public void update(ExpertProfileDTO expert) {

        List<String> savedFileNames = new ArrayList<>();
        String uploadDir = "C:/upload/expert";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 1. 새 파일 저장
        if (expert.getNewFiles() != null) {
            for (MultipartFile file : expert.getNewFiles()) {
                if (!file.isEmpty()) {
                    try {
                        String originalName = file.getOriginalFilename();
                        File dest = new File(uploadDir, originalName);
                        file.transferTo(dest);
                        savedFileNames.add(originalName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 2. 삭제 파일 처리
        if (expert.getDeleteFiles() != null) {
            for (String fileName : expert.getDeleteFiles()) {
                File file = new File(uploadDir, fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        // ✅ 3. 최종 파일명 리스트 = 기존 - 삭제 + 추가
        List<String> finalFileList = new ArrayList<>();
        if (expert.getFileNames() != null) {
            finalFileList.addAll(expert.getFileNames()); // 기존 유지
        }

        if (expert.getDeleteFiles() != null) {
            finalFileList.removeAll(expert.getDeleteFiles()); // 삭제 제거
        }

        finalFileList.addAll(savedFileNames); // 새 파일 추가

        String fileNamesStr = finalFileList.isEmpty() ? null : String.join(",", finalFileList);

        // 4. 시간
        Timestamp submittedAt = expert.getSubmittedAt() != null
            ? Timestamp.valueOf(expert.getSubmittedAt())
            : Timestamp.valueOf(LocalDateTime.now());

        // 5. DB 업데이트
        String sql = "UPDATE expert_profile SET career = ?, university = ?, certification = ?, introduction = ?, file_names = ?, submitted_at = ? WHERE member_id = ?";

        template.update(sql,
            expert.getCareer(),
            expert.getUniversity(),
            expert.getCertification(),
            expert.getIntroduction(),
            fileNamesStr,
            submittedAt,
            expert.getMemberId()
        );
    }




    
}
