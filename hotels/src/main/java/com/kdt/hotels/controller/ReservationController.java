package com.kdt.hotels.controller;

import com.kdt.hotels.dao.ReservationDAO;
import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/reserve") //reservation
public class ReservationController {
    private final ReservationDAO reservationDAO;
    private final RoomDAO roomDAO;

    public ReservationController(ReservationDAO reservationDao, RoomDAO roomDAO) {
        this.reservationDAO = reservationDao;
        this.roomDAO = roomDAO;
    }

    // 유저 예약 리스트
    @GetMapping("/userReservationList")
    public String userReservationList(Model model, HttpSession session){
        List<ReservationVO> reserveList = reservationDAO.userReservationList(session.getId());
        model.addAttribute("reserveList", reserveList);
        return "thymeleaf/userReservationManage";
    }
    // 호텔 예약
    @GetMapping("/reserveHotel")
    public String insertViewReserve(Model model) {
        model.addAttribute("selectRoom", new ReservationVO());
        return "HotelList/selectRoom";
    }

    // 호텔 예약 결과
    @PostMapping("/reserveHotel")
    public String reserveHotel(
            @RequestParam("hotelId") int hotelId,
            @RequestParam("userID") String userID,
            @RequestParam("startdate") String startDate,
            @RequestParam("enddate") String endDate,
            @RequestParam("roomID") int roomId,
            Model model) {

        // Create a ReservationVO object and set the properties
        ReservationVO reservation = new ReservationVO();
        reservation.setHotelID(hotelId);
        reservation.setUserID(userID);
        reservation.setStartDate(Date.valueOf(startDate));
        reservation.setEndDate(Date.valueOf(endDate));
        reservation.setRoomid(roomId);


        model.addAttribute("reservation", reservation);
        boolean isSuccess = reservationDAO.reserveHotel(reservation);
        model.addAttribute("isSuccess", isSuccess);

        return "HotelList/reserveResult"; // Change this to your actual confirmation page
    }

    // 유저 예약 수정 페이지
    @GetMapping("/userReservationUpdate")
    public String userReservationUpdate(Model model, @RequestParam("reserveID") int reserveID, @RequestParam("hotelID") int hotelId, @RequestParam("hotelName") String hotelName){
        model.addAttribute("reserveID", reserveID);
        model.addAttribute("hotelName", hotelName);
        List<RoomVO> avaRoom = roomDAO.avaRoom(hotelId);
        model.addAttribute("avaRoom",avaRoom);
        return "thymeleaf/userReservationUpdate";
    }

    // 유저 에약 수정 DB
    @PostMapping("/userReservationUpdate")
    public String userReservationDBUpdate(@RequestParam("reserveID") int reserveID, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, @RequestParam("roomID") int roomID, Model model) {
        ReservationVO vo = new ReservationVO();
        vo.setReserveID(reserveID);
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);
        vo.setRoomid(roomID);
        model.addAttribute("reservationUpdate", vo);
        reservationDAO.userReservationUpdate(vo);
        return "redirect:/reserve/userReservationList";
    }
}
