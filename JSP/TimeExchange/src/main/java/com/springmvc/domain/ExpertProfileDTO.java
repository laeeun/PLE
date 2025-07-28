package com.springmvc.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExpertProfileDTO {
    
    private Long id;                 // 전문가 프로필 고유 ID
    private String memberId;        // 연관된 회원 ID (FK)

    private String career;          // 경력 사항
    private String university;      // 출신 대학교
    private String certification;   // 자격증, 활동 등
    private String introduction;    // 자기소개 ✅

    private List<String> fileNames; // 첨부 파일명 리스트
    private List<String> deleteFiles;              // 삭제할 파일 이름들
    private List<MultipartFile> newFiles;          // 새로 업로드한 파일들

    private LocalDateTime submittedAt;  // 제출 시각

    // ✅ 기본 생성자
    public ExpertProfileDTO() {}

    // ✅ 생성자 (필수 정보만 받는 형태)
    public ExpertProfileDTO(String memberId, String career, String university, String certification, String introduction, List<String> fileNames) {
        this.memberId = memberId;
        this.career = career;
        this.university = university;
        this.certification = certification;
        this.introduction = introduction;
        this.fileNames = fileNames;
        this.submittedAt = LocalDateTime.now();
    }

    // ✅ getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

	public List<String> getDeleteFiles() {
		return deleteFiles;
	}

	public void setDeleteFiles(List<String> deleteFiles) {
		this.deleteFiles = deleteFiles;
	}

	public List<MultipartFile> getNewFiles() {
		return newFiles;
	}

	public void setNewFiles(List<MultipartFile> newFiles) {
		this.newFiles = newFiles;
	}

	@Override
	public String toString() {
		return "ExpertProfileDTO [id=" + id + ", memberId=" + memberId + ", career=" + career + ", university="
				+ university + ", certification=" + certification + ", introduction=" + introduction + ", fileNames="
				+ fileNames + ", deleteFiles=" + deleteFiles + ", newFiles=" + newFiles + ", submittedAt=" + submittedAt
				+ "]";
	}
    
    
}
