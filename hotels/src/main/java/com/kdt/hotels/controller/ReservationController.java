package com.kdt.hotels.controller;

import com.kdt.hotels.dao.ReservationDAO;
import com.kdt.hotels.vo.ReservationVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hotel")
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
}
