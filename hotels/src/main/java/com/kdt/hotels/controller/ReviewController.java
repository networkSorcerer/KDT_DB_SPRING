package com.kdt.hotels.controller;


import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.ReservationDAO;
import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewDAO reviewDAO;
    private final ReservationDAO reservationDAO;
    private final HotelDAO hotelDAO;

    public ReviewController(ReviewDAO reviewDAO, ReservationDAO reservationDAO, HotelDAO hotelDAO) {
        this.reviewDAO = reviewDAO;
        this.reservationDAO = reservationDAO;
        this.hotelDAO = hotelDAO;
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

    // 유저 리뷰 등록 - 예약 목록 페이지
    @GetMapping("/userReviewReserveList")
    public String userReviewReserveList(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        List<ReservationVO> reserveList = reservationDAO.userReservationList(userID);
        model.addAttribute("reserveList", reserveList);
        return "thymeleaf/userReviewReserveList";
    }
    @PostMapping("/userReviewReserveList")
    public String userReviewReserveListMove(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        List<ReservationVO> reserveList = reservationDAO.userReservationList(userID);
        model.addAttribute("reserveList", reserveList);
        return "redirect:/review/userReviewReserveList";
    }

    // 유저 리뷰 등록 - 등록하는 페이지
    @GetMapping("/userReviewInsert")
    public String userReviewInsert(Model model, HttpSession session, @RequestParam("hotelID") int hotelID){
        String userID = (String)session.getAttribute("userid");
        HotelVO hotelVO = hotelDAO.hotelSelectOne(hotelID);
        List<ReviewVO> reviewReserveList = reviewDAO.userReviewReserveList(userID, hotelID);
        model.addAttribute("hotelVO", hotelVO);
        model.addAttribute("reviewReserveList", reviewReserveList);
        return "thymeleaf/userReviewInsert";
    }

    // 유저 리뷰 등록 DB
    @PostMapping("/userReviewInsert")
    public String userReviewInsertDB(Model model, HttpSession session, @RequestParam("hotelID") int hotelID, @RequestParam("hotelName") String hotelName, @RequestParam("content") String content, @RequestParam("star") int star){
        String userID = (String)session.getAttribute("userid");
        ReviewVO vo = new ReviewVO(0, hotelID, hotelName, userID, content, star);
        reviewDAO.userReviewInsert(vo);
        return "redirect:/review/userReviewList";
    }

    // 유저 리뷰 수정 페이지
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
}
