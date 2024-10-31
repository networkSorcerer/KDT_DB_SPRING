package com.kdt.hotels.controller;


import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.ReservationDAO;
import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.ReservationVO;
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
//    @GetMapping("/hotelReview")
//    public String insertViewReview(Model model){
//        model.addAttribute("review", new ReviewVO());
//        return "/HotelList/selectRoom";
//    }
    // 호텔 예약 페이지에서 리뷰 등록
    @PostMapping("/hotelReview")
    public String hoteReview(Model model, RedirectAttributes redirectAttributes,

                             @RequestParam("hotelId") int hotelId,
                             @RequestParam("userid") String userid,
                             @RequestParam("content") String content,
                             @RequestParam("star") int star,HttpSession session) {
        ReviewVO review = new ReviewVO();

        review.setHotelID(hotelId);
        review.setUserID(userid);
        review.setContent(content);
        review.setStar(star);
        //boolean checkReserve =
        boolean isSuccess = reviewDAO.reviewWrite(review);
        String hotelName = (String) session.getAttribute("hotelName");

        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("review", review);
        redirectAttributes.addAttribute("hotelId", hotelId); // 호텔 ID 추가
        redirectAttributes.addAttribute("hotelName", "호텔 이름"); // 예시로 호텔 이름 추가
        redirectAttributes.addAttribute("isSuccess", isSuccess); // 성공 여부 추가

        return "redirect:/hotel/selectRoom"; // 리다이렉트할 URL
    }

    // 호텔 리뷰 수정 DB
    @PostMapping("/hotelReviewUpdate")
    public String hotelReviewUpdate(Model model,  RedirectAttributes redirectAttributes,
                                    @RequestParam("reviewID") int reviewID,
                                    @RequestParam("star") int star,
                                    @RequestParam("content") String content,
                                    @RequestParam("hotelId") int hotelId,
                                    @RequestParam("hotelName") String hotelName
            , HttpSession session){
        System.out.println(reviewID);

        ReviewVO vo = new ReviewVO();

        vo.setReviewID(reviewID);
        vo.setStar(star);
        vo.setContent(content);
        model.addAttribute("reviewUpdate", vo);
        reviewDAO.userReviewUpdate(vo);

        redirectAttributes.addAttribute("hotelId", hotelId); // 호텔 ID 추가
        redirectAttributes.addAttribute("hotelName", hotelName); // 예시로 호텔 이름 추가
        return "redirect:/hotel/selectRoom";
    }
}
