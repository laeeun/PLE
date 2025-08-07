package com.springmvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.enums.MemberStatus;

public class Member {

    private String member_id;
    private String userName;
    private String pw;
    private String pwConfirm; // 추가: 비밀번호 확인 필드
    private String name;

    private String emailId;
    private String emailDomain;
    private String email;

    private String phone;
    private boolean phoneVerified = false;

    // phone1, phone2, phone3 필드는 그대로 유지하여 @ModelAttribute 바인딩에 사용
    private String phone1;
    private String phone2;
    private String phone3;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 폼 요청일 경우
    private LocalDate birthDate;

    private String gender; // 성별 ("M", "F")

    private String addr;
    private String addrDetail; // 상세주소

    private boolean expert; // 기본값: 일반 사용자

    private int account = 0;

    private LocalDateTime createdAt; // DB 자동 생성, 필요 시 조회용 현재시간으로 초기화해서 바꾸기

    private String emailToken;
    private LocalDateTime tokenCreatedAt;
    private String role;
    private boolean emailVerified = false;

    public boolean isTokenExpired(int limitMinutes) {
        if (this.tokenCreatedAt == null) return true;
        return this.tokenCreatedAt.isBefore(LocalDateTime.now().minusMinutes(limitMinutes));
    }

    private int loginFailCount; // 로그인 실패 횟수
    private LocalDateTime lastFailTime; // 마지막 실패 시간

    //실제 파일 객체 받기
    private MultipartFile profileImageFile;

    //DB에 저장할 파일명
    private String profileImage;

    private String tempPassword;                // 발급된 임시 비밀번호 (실제로는 암호화된 pw와 비교하는 용도)
    private LocalDateTime tempPwCreatedAt;      // 임시 비밀번호 생성 시각
    
    private MemberStatus status;

    public Member() {
        this.createdAt = LocalDateTime.now(); // 현재 시간으로 초기화
    }

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPwConfirm() {
		return pwConfirm;
	}

	public void setPwConfirm(String pwConfirm) {
		this.pwConfirm = pwConfirm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public boolean isExpert() {
		return expert;
	}

	public void setExpert(boolean expert) {
		this.expert = expert;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	public LocalDateTime getTokenCreatedAt() {
		return tokenCreatedAt;
	}

	public void setTokenCreatedAt(LocalDateTime tokenCreatedAt) {
		this.tokenCreatedAt = tokenCreatedAt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public int getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public LocalDateTime getLastFailTime() {
		return lastFailTime;
	}

	public void setLastFailTime(LocalDateTime lastFailTime) {
		this.lastFailTime = lastFailTime;
	}

	public MultipartFile getProfileImageFile() {
		return profileImageFile;
	}

	public void setProfileImageFile(MultipartFile profileImageFile) {
		this.profileImageFile = profileImageFile;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public LocalDateTime getTempPwCreatedAt() {
		return tempPwCreatedAt;
	}

	public void setTempPwCreatedAt(LocalDateTime tempPwCreatedAt) {
		this.tempPwCreatedAt = tempPwCreatedAt;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", userName=" + userName + ", pw=" + pw + ", pwConfirm=" + pwConfirm
				+ ", name=" + name + ", emailId=" + emailId + ", emailDomain=" + emailDomain + ", email=" + email
				+ ", phone=" + phone + ", phoneVerified=" + phoneVerified + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", phone3=" + phone3 + ", birthDate=" + birthDate + ", gender=" + gender + ", addr=" + addr
				+ ", addrDetail=" + addrDetail + ", expert=" + expert + ", account=" + account + ", createdAt="
				+ createdAt + ", emailToken=" + emailToken + ", tokenCreatedAt=" + tokenCreatedAt + ", role=" + role
				+ ", emailVerified=" + emailVerified + ", loginFailCount=" + loginFailCount + ", lastFailTime="
				+ lastFailTime + ", profileImageFile=" + profileImageFile + ", profileImage=" + profileImage
				+ ", tempPassword=" + tempPassword + ", tempPwCreatedAt=" + tempPwCreatedAt + ", status=" + status
				+ "]";
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}