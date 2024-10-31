package com.kdt.hotels.controller;


import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewDAO reviewDAO;

    public ReviewController(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    // 유저 리뷰 리스트
    @GetMapping("/userReviewList")
    public String userReviewList(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        List<ReviewVO> reviewList = reviewDAO.userReviewList(userID);
        model.addAttribute("reviewList", reviewList);
        return "thymeleaf/userReviewManage";
    }
    @PostMapping("/userReviewList")
    public String userReviewListMove(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        List<ReviewVO> reviewList = reviewDAO.userReviewList(userID);
        model.addAttribute("reviewList", reviewList);
        return "redirect:/review/userReviewList";
    }

    //  유저 리뷰 수정 페이지
    @GetMapping("/userReviewUpdate")
    public String userReviewUpdate(Model model, @RequestParam("reviewID") int reviewID, @RequestParam("hotelID") int hotelID, @RequestParam("hotelName") String hotelName, @RequestParam("star") int star, @RequestParam("content") String content){
        model.addAttribute("reviewID", reviewID);
        model.addAttribute("hotelID", hotelID);
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("star", star);
        model.addAttribute("content", content);
        return "thymeleaf/userReviewUpdate";
    }

    // 유저 리뷰 수정 DB
    @PostMapping("/userReviewUpdate")
    public String userReviewDBUpdate(Model model,@RequestParam("reviewID") int reviewID, @RequestParam("star") int star, @RequestParam("content") String content){
        ReviewVO vo = new ReviewVO();
        vo.setReviewID(reviewID);
        vo.setStar(star);
        vo.setContent(content);
        model.addAttribute("reviewUpdate", vo);
        reviewDAO.userReviewUpdate(vo);
        return "redirect:/review/userReviewList";
    }

    // 유저 리뷰 삭제 DB
    @PostMapping("/userReviewDelete")
    public String userReviewDelete(Model model, @RequestParam("reviewID") int reviewID){
        model.addAttribute("reviewID", reviewID);
        reviewDAO.userReviewDelete(reviewID);
        return "redirect:/review/userReviewList";
    }
    @GetMapping("/hotelReview")
    public String insertViewReview(Model model){
        model.addAttribute("review", new ReviewVO());
        return "/HotelList/selectRoom";
    }
    @PostMapping("/hotelReview")
    public String hoteReview(Model model, RedirectAttributes redirectAttributes,
                             @RequestParam("hotelid") int hotelid,
                             @RequestParam("userid") String userid,
                             @RequestParam("content") String content,
                             @RequestParam("star") int star) {
        ReviewVO review = new ReviewVO();
        review.setHotelID(hotelid);
        review.setUserID(userid);
        review.setContent(content);
        review.setStar(star);

        boolean isSuccess = reviewDAO.reviewWrite(review);
        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("review", review);
        redirectAttributes.addAttribute("hotelId", hotelid); // 호텔 ID 추가
        redirectAttributes.addAttribute("hotelName", "호텔 이름"); // 예시로 호텔 이름 추가
        redirectAttributes.addAttribute("isSuccess", isSuccess); // 성공 여부 추가

        return "redirect:/hotel/selectRoom"; // 리다이렉트할 URL
    }
}
