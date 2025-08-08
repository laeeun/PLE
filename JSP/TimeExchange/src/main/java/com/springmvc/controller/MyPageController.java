package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.ExpertProfileDTO;
import com.springmvc.domain.HistoryDTO;
import com.springmvc.domain.Member;
import com.springmvc.domain.PasswordHistoryDTO;
import com.springmvc.service.ExpertProfileService;
import com.springmvc.service.HistoryService;
import com.springmvc.service.MemberService;
import com.springmvc.service.PasswordHistoryService;
import com.springmvc.service.ReviewService;

@Controller
@RequestMapping("/mypage")
public class MyPageController  {
	@Autowired
	private MemberService memberService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private PasswordHistoryService passwordHistoryService;
    
    @Autowired
    private ExpertProfileService expertProfileService;
	
	@GetMapping
    public String myPage(HttpSession session, Model model) {
		Member sessionMember = (Member) session.getAttribute("loggedInUser");
	    if (sessionMember == null) {
	        return "redirect:/login";
	    }

	    String loginId = sessionMember.getMember_id();
	    Member member = memberService.findById(loginId);
	    model.addAttribute("member", member);
	    System.out.println(member);
	    // ✅ 전문가 프로필 추가
	    ExpertProfileDTO expertProfile = expertProfileService.findByMemberId(loginId);
	    if (expertProfile != null) {
	        model.addAttribute("expertProfile", expertProfile);
	    }
	    return "mypage";
    }
	
	
	@GetMapping("/edit")
	public String editForm(HttpSession session, Model model) {
	    Member login = (Member) session.getAttribute("loggedInUser");
	    if (login == null) {
	        return "redirect:/login"; // 로그인 안했으면 로그인 페이지로
	    }

	    Member member = memberService.findById(login.getMember_id());

	    // 이메일 분리
	    if (member.getEmail() != null && member.getEmail().contains("@")) {
	        String[] emailParts = member.getEmail().split("@");
	        member.setEmailId(emailParts[0]);
	        member.setEmailDomain(emailParts[1]);
	    }

	    // 전화번호 분리
	    if (member.getPhone() != null && member.getPhone().contains("-")) {
	        String[] phoneParts = member.getPhone().split("-");
	        if (phoneParts.length == 3) {
	            member.setPhone1(phoneParts[0]);
	            member.setPhone2(phoneParts[1]);
	            member.setPhone3(phoneParts[2]);
	        }
	    }
	    
	    if (member.getProfileImage() != null && member.getProfileImage().trim().isEmpty()) {
	        member.setProfileImage(null);
	    }
	    System.out.println(member);
	    
	    model.addAttribute("member", member);
	    model.addAttribute("mode", "edit");
	    
	    return "editForm";
	}

	
	@PostMapping("/edit")
	public String edit(@ModelAttribute Member member,
	                   HttpServletRequest request,
	                   RedirectAttributes redirectAttributes,
	                   HttpSession session, Model model,
	                   @RequestParam(value = "profileImageFile", required = false) MultipartFile file,
	                   @RequestParam(value = "resetProfile", required = false) String resetProfile) {

	    Member login = (Member) session.getAttribute("loggedInUser");
	    if (login == null) {
	        return "redirect:/login";
	    }

	    // ⭐ 닉네임 중복 검사 (자기 자신은 제외)
	    if (memberService.existsByUsernameExceptMe(member.getUserName(), member.getMember_id())) {
	        model.addAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
	        return "editForm";
	    }

	    // 이메일 조합
	    String emailId = request.getParameter("emailId");
	    String emailDomain = request.getParameter("emailDomain");
	    if (emailId != null && emailDomain != null) {
	        member.setEmail(emailId + "@" + emailDomain);
	        member.setEmailId(emailId);
	        member.setEmailDomain(emailDomain);
	    }

	    // 전화번호 조합
	    String phone1 = request.getParameter("phone1");
	    String phone2 = request.getParameter("phone2");
	    String phone3 = request.getParameter("phone3");
	    if (phone1 != null && phone2 != null && phone3 != null) {
	        member.setPhone1(phone1);
	        member.setPhone2(phone2);
	        member.setPhone3(phone3);
	        member.setPhone(phone1 + "-" + phone2 + "-" + phone3);
	    }

	    // 기존 정보 유지
	    member.setEmailVerified(login.isEmailVerified());
	    member.setRole(login.getRole());
	    member.setAccount(login.getAccount());
	    member.setCreatedAt(login.getCreatedAt());
	    member.setProfileImage(login.getProfileImage());

	    // === 업로드 처리 ===
	    final Path uploadDir = java.nio.file.Paths.get("c:/upload/profile/");
	    try {
	        java.nio.file.Files.createDirectories(uploadDir);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // ✅ 기본 이미지로 초기화 요청이 있다면
	    if ("true".equals(resetProfile)) {
	        // 기존 이미지가 있다면 삭제
	        if (login.getProfileImage() != null) {
	            try {
	                java.nio.file.Files.deleteIfExists(uploadDir.resolve(login.getProfileImage()));
	            } catch (IOException ignore) {}
	        }
	        // 기본 이미지로 설정
	        member.setProfileImage("default-profile.png");

	    } else if (file != null && !file.isEmpty()) {
	        // ✅ 새 이미지 업로드 처리
	        String original = file.getOriginalFilename();
	        String ext = (original != null && original.lastIndexOf('.') != -1)
	                ? original.substring(original.lastIndexOf('.')).toLowerCase()
	                : "";
	        String newName = java.util.UUID.randomUUID().toString().replace("-", "") + ext;

	        if (login.getProfileImage() != null) {
	            try {
	                java.nio.file.Files.deleteIfExists(uploadDir.resolve(login.getProfileImage()));
	            } catch (IOException ignore) {}
	        }

	        try {
	            file.transferTo(uploadDir.resolve(newName).toFile());
	        } catch (IllegalStateException | IOException e) {
	            e.printStackTrace();
	        }

	        member.setProfileImage(newName);

	    } else {
	        // 아무것도 안 하면 기존 이미지 유지
	        member.setProfileImage(login.getProfileImage());
	    }

	    // 전문가 여부 확인
	    boolean wasExpert = login.isExpert();
	    boolean nowExpert = member.isExpert();

	    // 업데이트
	    memberService.update(member);
	    session.setAttribute("loggedInUser", member);
	    System.out.println("✅ 기존 전문가 상태 (wasExpert): " + wasExpert);

	    // 전문가로 바뀐 경우, 프로필 정보 없으면 입력 폼 이동
	    if (!wasExpert && nowExpert) {
	        boolean hasProfile = expertProfileService.existsByMemberId(member.getMember_id());
	        if (!hasProfile) {
	            model.addAttribute("memberId", member.getMember_id());
	            model.addAttribute("expertProfile", new ExpertProfileDTO());
	            model.addAttribute("mode", "create");
	            return "expertForm";
	        }
	    }

	    redirectAttributes.addFlashAttribute("success", "회원 정보가 수정되었습니다.");
	    return "redirect:/mypage";
	}


	@GetMapping("/checkUsername")
	@ResponseBody
	public String checkUsername(@RequestParam String userName, HttpSession session) {
	    Member login = (Member) session.getAttribute("loggedInUser");
	    boolean exists = memberService.existsByUsernameExceptMe(userName, login.getMember_id());
	    return exists ? "duplicated" : "available";
	}

	
	@PostMapping("/delete")
    public String delete(HttpSession session) {
            Member loggedInUser = (Member) session.getAttribute("loggedInUser");
            String member_id = loggedInUser.getMember_id();
            memberService.delete(member_id);
            return "redirect:/mypage/deactivated";
    }


	 @PostMapping("/deactivated/restore")
    public String restore(HttpSession session) {
            Member loggedInUser = (Member) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return "redirect:/login";
            }
            String member_id = loggedInUser.getMember_id();
            memberService.restore(member_id);
            return "redirect:/mypage";
    }
	
	
    @GetMapping("/deactivated")
    public String deactivated() {
            return "deactivated";
    }
	
	@GetMapping("/changePw")
	public String changePw() {
		    return "changePasswordForm";
	}
	
	@PostMapping("/changePw")
	public String changePassword(@RequestParam String currentPw,
	                             @RequestParam String newPw,
	                             @RequestParam String pwConfirm,
	                             HttpSession session,
	                             RedirectAttributes redirectAttributes,
	                             Model model) {

	    Member loggedInUser = (Member) session.getAttribute("loggedInUser");
	    if (loggedInUser == null) {
	        return "redirect:/login";
	    }

	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	    // 1. 현재 비밀번호 일치 여부 확인
	    if (!encoder.matches(currentPw, loggedInUser.getPw())) {
	        redirectAttributes.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다.");
	        return "redirect:/mypage/changePw";
	    }

	    // 2. 새 비밀번호 두 개 일치 여부 확인
	    if (!newPw.equals(pwConfirm)) {
	        redirectAttributes.addFlashAttribute("error", "새 비밀번호가 일치하지 않습니다.");
	        return "redirect:/mypage/changePw";
	    }

	    // 3. 최근 3개 비밀번호 중복 체크
	    List<PasswordHistoryDTO> historyList = passwordHistoryService.findRecent3ByMemberId(loggedInUser.getMember_id());
	    for (PasswordHistoryDTO history : historyList) {
	        if (encoder.matches(newPw, history.getPassword_hash())) {
	        	 redirectAttributes.addFlashAttribute("error", "최근에 사용한 비밀번호는 사용할 수 없습니다.");
	            return "redirect:/mypage/changePw";
	        }
	    }

	    // 4. 암호화 후 비밀번호 변경
	    String encodedPw = encoder.encode(newPw);
	    memberService.updatePassword(loggedInUser.getMember_id(), encodedPw);

	    // 5. 세션 정보 최신화
	    Member updated = memberService.findById(loggedInUser.getMember_id());
	    session.setAttribute("loggedInUser", updated);

	    redirectAttributes.addFlashAttribute("success", "비밀번호가 성공적으로 변경되었습니다!");
	    return "redirect:/mypage";
	}

	
	@GetMapping("/history")
	public String history(HttpSession session, Model model) {
	    Member loggedInUser = (Member) session.getAttribute("loggedInUser");
	    if (loggedInUser == null) {
	        return "redirect:/login";
	    }

	    String member_id = loggedInUser.getMember_id();

	    List<HistoryDTO> historyList = historyService.findByMemberId(member_id);

	    for (HistoryDTO dto : historyList) {
	        // 구매자인 경우에만 리뷰 작성 여부 확인
	        if (dto.getBuyer_id().equals(member_id)) {
	            boolean exists = reviewService.existsByHistoryId(dto.getHistory_id());
	            dto.setReview_written(exists);

	            if (exists) {
	                Long reviewId = reviewService.findIdByHistoryId(dto.getHistory_id());
	                dto.setReview_id(reviewId);
	            } else {
	                dto.setReview_id(null);
	            }
	        } else {
	            dto.setReview_written(true); // 판매자는 항상 "받은 리뷰 보기"로 간주
	        }
	    }

	    model.addAttribute("historyList", historyList);
	    model.addAttribute("loggedInUser", loggedInUser);

	    return "History";
	}
	
	@GetMapping("/expert/edit")
	public String expertEditForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
	    Member loginUser = (Member) session.getAttribute("loggedInUser");

	    if (loginUser == null) {
	        redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
	        return "redirect:/login";
	    }

	    String memberId = loginUser.getMember_id();
	    ExpertProfileDTO expertProfile = expertProfileService.findByMemberId(memberId);

	    // ✅ 전문가 정보가 없더라도 form 진입 허용
	    if (expertProfile == null) {
	        expertProfile = new ExpertProfileDTO(); // 빈 객체 생성해서 폼에 넘김
	    }

	    // ✅ 폼에 필요한 정보 전달
	    model.addAttribute("expertDTO", expertProfile);   // 비어있거나 기존 데이터
	    model.addAttribute("mode", "edit");               // edit 모드로 진입
	    model.addAttribute("memberId", memberId);         // 히든 input 용도

	    return "expertEdit";  // 전문가 정보 입력/수정 폼 JSP
	}




	@PostMapping("/expert/edit")
	public String expertEditSubmit(@ModelAttribute ExpertProfileDTO expertDTO,
	                               @RequestParam(value = "expertFiles", required = false) MultipartFile[] newFiles,
	                               HttpSession session,
	                               RedirectAttributes redirectAttributes) {

	    Member loginUser = (Member) session.getAttribute("loggedInUser");

	    if (loginUser == null) {
	        redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
	        return "redirect:/login";
	    }

	    // 로그인한 사용자 ID 보안상 재설정
	    expertDTO.setMemberId(loginUser.getMember_id());

	    // 새로 업로드된 파일들 저장
	    if (newFiles != null && newFiles.length > 0) {
	        expertDTO.setNewFiles(Arrays.asList(newFiles)); // ✨ DTO에 List<MultipartFile> 필드 필요
	    }

	    // 수정 서비스 로직 호출 (deleteFiles는 AJAX로 따로 처리)
	    expertProfileService.update(expertDTO);

	    redirectAttributes.addFlashAttribute("success", "전문가 정보가 성공적으로 수정되었습니다.");
	    return "redirect:/mypage";
	}
	
	@PostMapping("/expert/submit")
	public String expertSubmit(@RequestParam("memberId") String memberId,
	                           @RequestParam("career") String career,
	                           @RequestParam("university") String university,
	                           @RequestParam("certification") String certification,
	                           @RequestParam("introduction") String introduction,
	                           @RequestParam("expertFiles") List<MultipartFile> files,
	                           RedirectAttributes redirectAttributes) {

	    List<String> savedFileNames = new ArrayList<>();

	    if (files != null && !files.isEmpty()) {
	        for (MultipartFile file : files) {
	            if (!file.isEmpty()) {
	                try {
	                    String originalFilename = file.getOriginalFilename();
	                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	                    String savedName = timestamp + "_" + originalFilename;

	                    String uploadDir = "c:/upload/expert/";
	                    File dir = new File(uploadDir);
	                    if (!dir.exists()) dir.mkdirs();

	                    File dest = new File(uploadDir + savedName);
	                    file.transferTo(dest);

	                    savedFileNames.add(savedName);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    redirectAttributes.addFlashAttribute("error", "파일 업로드 중 오류가 발생했습니다.");
	                    return "redirect:/mypage";
	                }
	            }
	        }
	    }

	    ExpertProfileDTO dto = new ExpertProfileDTO(
	        memberId,
	        career,
	        university,
	        certification,
	        introduction,
	        savedFileNames
	    );

	    expertProfileService.save(dto);
	    redirectAttributes.addFlashAttribute("success", "전문가 정보가 저장되었습니다!");
	    return "redirect:/mypage";
	}

	@PostMapping("/expert/deleteFile")
	@ResponseBody
	public String deleteExpertFile(@RequestParam("fileName") String fileName,
	                               HttpSession session) {
	    Member loginUser = (Member) session.getAttribute("loggedInUser");
	    if (loginUser == null) {
	        return "unauthorized";
	    }

	    String memberId = loginUser.getMember_id();

	    // 1. 전문가 정보 조회
	    ExpertProfileDTO expert = expertProfileService.findByMemberId(memberId);
	    if (expert == null) {
	        return "fail";
	    }

	    // 2. 삭제할 파일이 실제로 있는지 확인 후 제거
	    List<String> currentFiles = expert.getFileNames();
	    if (currentFiles != null && currentFiles.contains(fileName)) {
	        currentFiles.remove(fileName);
	    } else {
	        return "fail";
	    }

	    // 3. 파일 삭제 (물리적 삭제)
	    File file = new File("C:/upload/expert", fileName);
	    if (file.exists()) {
	        file.delete();
	    }

	    // 4. 최종 저장된 파일명 문자열로 만들기
	    expert.setFileNames(currentFiles);
	    expert.setNewFiles(null); // 새 파일 없으니까 null
	    expert.setDeleteFiles(null); // 삭제 파일도 따로 안 넘김
	    expert.setSubmittedAt(LocalDateTime.now()); // 시간 갱신

	    expertProfileService.update(expert); // update로 DB 반영

	    return "success";
	}


	@PostMapping("/expert/update")
	public String updateExpert(
	    @ModelAttribute ExpertProfileDTO expertDTO,
	    @RequestParam(value = "expertFiles", required = false) List<MultipartFile> newFiles,
	    @RequestParam(value = "deleteFiles", required = false) List<String> deleteFiles,
	    HttpSession session,
	    RedirectAttributes redirectAttributes) {

	    Member loginUser = (Member) session.getAttribute("loggedInUser");

	    if (loginUser == null) {
	        redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
	        return "redirect:/login";
	    }

	    String memberId = loginUser.getMember_id();
	    expertDTO.setMemberId(memberId);

	    // ✅ 기존 파일명을 서비스에서 조회해서 DTO에 세팅
	    ExpertProfileDTO existing = expertProfileService.findByMemberId(memberId);
	    if (existing != null) {
	        expertDTO.setFileNames(existing.getFileNames()); // 기존 파일명 리스트 유지
	    }

	    if (newFiles != null && !newFiles.isEmpty()) {
	        expertDTO.setNewFiles(newFiles);
	    }

	    if (deleteFiles != null && !deleteFiles.isEmpty()) {
	        expertDTO.setDeleteFiles(deleteFiles);
	    }

	    expertProfileService.update(expertDTO);

	    redirectAttributes.addFlashAttribute("success", "전문가 정보가 수정되었습니다.");
	    return "redirect:/mypage";
	}



	
}
