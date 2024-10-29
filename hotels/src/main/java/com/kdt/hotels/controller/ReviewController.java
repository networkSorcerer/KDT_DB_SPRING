package com.kdt.hotels.controller;


import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
