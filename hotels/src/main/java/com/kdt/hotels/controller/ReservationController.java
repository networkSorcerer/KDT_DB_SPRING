package com.kdt.hotels.controller;

import com.kdt.hotels.dao.ReservationDAO;
import com.kdt.hotels.vo.ReservationVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reserve") //reservation
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDao) {
        this.reservationDAO = reservationDao;
    }

    // 유저 예약 리스트
    @GetMapping("/userReservationList")
    public String userReservationList(Model model){
        List<ReservationVO> reserveList = reservationDAO.userReservationList();
        model.addAttribute("reserveList", reserveList);
        return "thymeleaf/userReservationManage";
    }
    // 호텔 예약
    @GetMapping("/reserveHotel")
    public String insertViewReserve(Model model) {
        ReservationVO reservationVO = new ReservationVO();
        model.addAttribute("selectRoom", reservationVO);
        System.out.println("ReservationVO added to model: " + reservationVO);
        return "HotelList/selectRoom";
    }

    // 호텔 예약 결과
    @PostMapping("/reserveHotel")
    public String insertDBReserve (@ModelAttribute("selectRoom") ReservationVO empVO, Model model) {
        boolean isSuccess = reservationDAO.reserveHotel(empVO);
        model.addAttribute("isSuccess",isSuccess);
        return "HotelList/reserveResult";
    }

    // 유저 예약 수정 페이지
    @GetMapping("/userReservationUpdate")
    public String userReservationUpdate(Model model){
        model.addAttribute("reserveUpdate", new ReservationVO());
        return "thymeleaf/userReservationUpdate";
    }

    // 유저 에약 DB 수정
//    @PostMapping("/userReservationUpdate")
//    public String userReservationDBUpdate(@ModelAttribute("reserveUpdate") ReservationVO reservationVO, Model model) {
//        model.addAttribute()
//    }
}
