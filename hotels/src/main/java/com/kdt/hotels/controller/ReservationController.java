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
@RequestMapping("/hotel") //reservation
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDao) {
        this.reservationDAO = reservationDao;
    }

    @GetMapping("/userReservationList")
    public String userReservationList(Model model){
        List<ReservationVO> reserveList = reservationDAO.userReservationList();
        model.addAttribute("reserveList", reserveList);
        return "thymeleaf/userReservationManage";
    }
    @GetMapping("/reserveHotel")
    public String insertViewReserve(Model model) {
        model.addAttribute("employees", new ReservationVO());
        return "HotelList/reserveHotel";
    }
    @PostMapping("/reserveHotel")
    public String insertDBReserve (@ModelAttribute("reserveHotel") ReservationVO empVO, Model model) {
        boolean isSuccess = reservationDAO.reserveHotel(empVO);
        model.addAttribute("isSuccess",isSuccess);
        return "HotelList/reserveResult";
    }
}
