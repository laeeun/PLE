package com.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springmvc.domain.FavoriteDTO;
import com.springmvc.domain.Member;
import com.springmvc.service.FavoriteService;
import com.springmvc.service.TalentService;

@Controller // View(JSP)와 연결되는 전통적인 컨트롤러
@RequestMapping("/favorite") // 이 컨트롤러의 모든 경로는 "/favorite"으로 시작함
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService; // 즐겨찾기 관련 비즈니스 로직 처리 서비스

    @Autowired
    TalentService talentService; // 재능 게시글 관련 서비스 (현재는 이 컨트롤러에서는 직접 사용 안 함)

    /**
     * ✅ [GET] 즐겨찾기 목록 페이지
     * - 로그인된 사용자의 즐겨찾기한 재능들을 페이지 단위로 불러옴
     * - JSP 뷰: myFavorite.jsp 렌더링
     */
    @GetMapping
    public String showFavoriteList(@RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model, HttpSession session) {
        Member user = (Member) session.getAttribute("loggedInUser"); // 로그인 사용자 정보
        if (user == null) return "redirect:/login"; // 로그인 안했으면 로그인 페이지로 이동

        int size = 6; // 한 페이지당 6개씩 보여줌
        int offset = (page - 1) * size; // 몇 번째부터 가져올지 offset 계산

        List<FavoriteDTO> myFavoriteList = favoriteService.readPagedFavoriteList(user.getMember_id(), offset, size); // 페이지별 즐겨찾기 목록 조회
        int totalCount = favoriteService.getFavoriteCount(user.getMember_id()); // 전체 즐겨찾기 수
        int totalPage = (int) Math.ceil((double) totalCount / size); // 전체 페이지 수 계산

        Map<String, Object> map = new HashMap<>();
        map.put("myFavoriteList", myFavoriteList);
        map.put("currentPage", page);
        map.put("totalPage", totalPage);

        model.addAllAttributes(map); // 한꺼번에 Model에 값 추가

        return "myFavorite"; // 뷰 파일명 (myFavorite.jsp)으로 이동
    }

    /**
     * ✅ [POST] 즐겨찾기 삭제 (AJAX)
     * - 마이페이지에서 삭제 버튼 클릭 시 호출
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> deleteFavorite(@RequestBody Map<String, Object> body, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        try {
            Member user = (Member) session.getAttribute("loggedInUser");
            if (user == null) {
                result.put("status", "unauthorized"); // 로그인 안 했으면
                return result;
            }

            String memberId = user.getMember_id();
            long talentId = Long.parseLong(body.get("talentId").toString()); // 요청 body에서 talentId 가져오기

            if (favoriteService.exists(memberId, talentId)) {
                favoriteService.deleteFavoriteTalent(memberId, talentId); // 즐겨찾기 삭제
                result.put("status", "success");
            } else {
                result.put("status", "not_found"); // 이미 존재하지 않음
            }
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("error", e.getMessage());
        }
        return result; // JSON 형태로 응답
    }

    /**
     * ✅ [POST] 즐겨찾기 토글 (추가 / 삭제 자동 전환)
     * - 재능글 상세 페이지에서 하트 아이콘 클릭 시 호출됨
     * - 존재하면 삭제, 없으면 추가
     */
    @PostMapping("/toggle")
    @ResponseBody
    public Map<String, String> toggleFavorite(@RequestBody Map<String, Object> body, HttpSession session) {
        Map<String, String> result = new HashMap<>();

        try {
            Member user = (Member) session.getAttribute("loggedInUser");
            if (user == null) {
                result.put("status", "unauthorized"); // 로그인 안 했으면
                return result;
            }

            String memberId = user.getMember_id();
            long talentId = Long.parseLong(body.get("talentId").toString());

            if (favoriteService.exists(memberId, talentId)) {
                favoriteService.deleteFavoriteTalent(memberId, talentId); // 이미 있으면 삭제
                result.put("status", "removed");
            } else {
                // 없으면 새로 추가
                FavoriteDTO dto = new FavoriteDTO();
                dto.setMemberId(memberId);
                dto.setTalentId(talentId);
                favoriteService.saveFavoriteTalent(dto); // DB에 추가
                result.put("status", "added");
            }
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("error", e.getMessage());
        }

        return result;
    }
}
