package com.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springmvc.domain.Member;
import com.springmvc.domain.TalentDTO;
import com.springmvc.service.FavoriteService;
import com.springmvc.service.FollowService;
import com.springmvc.service.MemberService;
import com.springmvc.service.NotificationService;
import com.springmvc.service.ReviewService;
import com.springmvc.service.TalentService;

@Controller
public class TalentController {

    @Autowired
    private TalentService talentService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private NotificationService notificationService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FollowService followService;
	
    // ✅ 재능 목록 + 필터 + 검색 통합
    @GetMapping("/talent")
    public String talentList(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "6") int size,
        @RequestParam(value = "expert", defaultValue = "all") String expert,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "keyword", required = false) String keyword,
        Model model,
        HttpSession session) {

        Member user = (Member) session.getAttribute("loggedInUser");

        List<TalentDTO> list = talentService.readTalents(page, size, expert, category, keyword);
        int totalCount = talentService.getTalentCount(expert, category, keyword);
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        for (TalentDTO dto : list) {
            talentService.formatTimeSlot(dto);
        }
        
        attachFavoriteInfo(list, user);
        
        
        model.addAttribute("talentList", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("expert", expert);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);

        return "talent";
    }

    // ✅ /talent/카테고리 요청 → redirect:/talent?category=카테고리
    @GetMapping("/talent/{category}")
    public String redirectCategory(@PathVariable("category") String category) {
    	try {
            category = URLDecoder.decode(category, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/talent?category=" + category;
    }

    // ✅ 재능 상세 보기
    @GetMapping("/talent/view")
    public String viewTalent(@RequestParam("id") int id, Model model, HttpSession session) {
        TalentDTO talent = talentService.readone(id);
        LocalDateTime ldt = talent.getCreated_at();
        Date createdDate = java.sql.Timestamp.valueOf(ldt);
        Member user = (Member) session.getAttribute("loggedInUser");
        
        talentService.formatTimeSlot(talent);

        boolean isFavorite = user != null && favoriteService.exists(user.getMember_id(), id);
        
        int reviewCount = reviewService.countByTalentId((long) id);
        double averageRating = reviewService.getAverageRatingByTalentId((long) id);
        
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("talent", talent);
        model.addAttribute("createdDate", createdDate);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("averageRating", averageRating);
        return "viewTalent";
    }

    // ✅ 재능 등록 폼 요청
    @GetMapping("/addtalent")
    public String requestAddTalentForm(@ModelAttribute("newTalent") TalentDTO dto) {
        return "addTalent";
    }

    // ✅ 재능 등록 처리
    @PostMapping("/addtalent")
    public String submitTalentForm(@ModelAttribute("newTalent") TalentDTO dto, HttpSession session, HttpServletRequest request) {
        Member loginUser = (Member) session.getAttribute("loggedInUser");
        MultipartFile file = dto.getUploadFile();
        dto.setMember_id(loginUser.getMember_id());
        dto.setCreated_at(LocalDateTime.now());
        // ✅ 파일 저장 처리
        if (file != null && !file.isEmpty()) {
            try {
                String uploadPath = request.getServletContext().getRealPath("/resources/uploads/");
                File directory = new File(uploadPath);
                if (!directory.exists()) directory.mkdirs();

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadPath, fileName);
                file.transferTo(saveFile);

                dto.setFilename(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // ✅ 시간 슬롯 처리 및 등록
        talentService.formatTimeSlot(dto);
        talentService.create(dto);
        // ✅ 팔로우 여부 및 알림
        String followingId = dto.getMember_id(); // 본인 ID
        boolean isNowFollowing = followService.toggleFollow(loginUser.getMember_id(), followingId);
        if (!loginUser.getMember_id().equals(followingId)) {
        	if (isNowFollowing) {
	            notificationService.createSimpleNotification(
	                followingId, // 팔로우 대상 ID
	                loginUser.getUserName(), // 알림 보낸 사람
	                "재능",
	                loginUser.getUserName() + "님이 게시글을 등록했습니다.",
	                null,
	                null
	            );
	        }
        }
        return "redirect:/talent";
    }

    // ✅ 재능 삭제
    @GetMapping("/talent/delete")
    public String deleteTalent(@RequestParam("id") int id) {
        talentService.delete(id);
        return "redirect:/talent";
    }

    // ✅ 재능 수정 폼 요청
    @GetMapping("/talent/update")
    public String getUpdateTalentForm(@RequestParam("id") int id, Model model) {
        TalentDTO talent = talentService.readone(id);
        model.addAttribute("updateTalent", talent);
        return "updateForm";
    }

    // ✅ 재능 수정 처리
    @PostMapping("/talent/update")
    public String submitUpdate(@ModelAttribute("updateTalent") TalentDTO dto, HttpServletRequest request) {
    	
    	MultipartFile file = dto.getUploadFile();
        String uploadPath = request.getServletContext().getRealPath("/resources/uploads/");

        // 업로드 폴더 없으면 생성
        File dir = new File(uploadPath);
        
        if (!dir.exists()) dir.mkdirs();
        
        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제
            if (dto.getFilename() != null) {
                File oldFile = new File(uploadPath, dto.getFilename());
                boolean deleted = oldFile.delete();
                System.out.println("기존 파일 삭제 성공 여부: " + deleted);
            }

            // 새 파일 저장
            String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            System.out.println("새 파일 이름: " + newFileName);
            File saveFile = new File(uploadPath, newFileName);
            try {
                file.transferTo(saveFile);
                System.out.println("새 파일 저장 성공 여부: " + saveFile.exists());
                dto.setFilename(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        talentService.update(dto);
        return "redirect:/talent/view?id=" + dto.getTalent_id();
    }
    
    // ✅ 로그인 유저 기준 찜 여부 세팅
    private void attachFavoriteInfo(List<TalentDTO> list, Member user) {
        if (user == null) return;
        for (TalentDTO dto : list) {
            boolean isFavorite = favoriteService.exists(user.getMember_id(), dto.getTalent_id());
            dto.setFavorite(isFavorite);
        }
    }
}
