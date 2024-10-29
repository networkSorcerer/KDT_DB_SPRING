package com.kdt.hotels.controller;


import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String userReviewList(Model model){
        List<ReviewVO> reviewList = reviewDAO.userReviewList();
        model.addAttribute("reviewList", reviewList);
        return "thymeleaf/userReviewManage";
    }

    // 리뷰 수정 페이지
    @GetMapping("/userReviewUpdate")
    public String userReviewUpdate(Model model, @RequestParam("reviewID") int reviewID, @RequestParam("hotelID") int hotelID, @RequestParam("hotelName") String hotelName, @RequestParam("star") int star, @RequestParam("content") String content){
        model.addAttribute("reviewID", reviewID);
        model.addAttribute("hotelID", hotelID);
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("star", star);
        model.addAttribute("content", content);
        return "";
    }
}
