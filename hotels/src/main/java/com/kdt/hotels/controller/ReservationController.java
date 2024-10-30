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
        String userID = (String)session.getAttribute("userid");
        List<ReservationVO> reserveList = reservationDAO.userReservationList(userID);
        model.addAttribute("reserveList", reserveList);
        return "thymeleaf/userReservationManage";
    }
    @PostMapping("/userReservationList")
    public String userReservationListMove(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        List<ReservationVO> reserveList = reservationDAO.userReservationList(userID);
        model.addAttribute("reserveList", reserveList);
        return "redirect:/reserve/userReservationList";
    }

    // 호텔 예약
    @GetMapping("/reserveHotel")
    public String insertViewReserve(Model model) {
        model.addAttribute("selectRoom", new ReservationVO());
        return "HotelList/selectRoom";
    }

    // 예약 가능한 방 조회
    @PostMapping("/reserveHotel")
    public String reserveHotel(
            @RequestParam("hotelId") int hotelId,
            @RequestParam("userid") String userid1,
            @RequestParam("startdate") String startDate,
            @RequestParam("enddate") String endDate,
            Model model, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        if (userid == null && userid1 != null) {
            userid = userid1;
        }

        // Create a ReservationVO object and set the properties
        ReservationVO reservation = new ReservationVO();
        reservation.setHotelID(hotelId);
        reservation.setUserID(userid);
        reservation.setStartDate(Date.valueOf(startDate));
        reservation.setEndDate(Date.valueOf(endDate));


        model.addAttribute("reservation", reservation);
        List<RoomVO>avaRooms =roomDAO.chooseRoom(reservation);
        System.out.println("avaRooms"+avaRooms);
        System.out.println("Available Rooms Count: " + avaRooms.size());

        model.addAttribute("avaRooms",avaRooms);
        return "HotelList/avaRoom"; // Change this to your actual confirmation page
    }

    // 유저 예약 수정 페이지
    @GetMapping("/userReservationUpdate")
    public String userReservationUpdate(Model model, @RequestParam("reserveID") int reserveID, @RequestParam("hotelID") int hotelId, @RequestParam("hotelName") String hotelName, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, @RequestParam("roomid") int roomid){
        model.addAttribute("reserveID", reserveID);
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("roomid", roomid);
        List<RoomVO> avaRoom = roomDAO.avaRoom(hotelId);
        model.addAttribute("avaRoom",avaRoom);
        return "thymeleaf/userReservationUpdate";
    }

    // 유저 예약 수정 DB
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

    // 유저 예약 삭제
    @PostMapping("/userReservationDelete")
    public String userReservationDelete(Model model, @RequestParam("reserveID") int reserveID){
        model.addAttribute("reservationDelete", reserveID);
        reservationDAO.userReservationDelete(reserveID);
        return "redirect:/reserve/userReservationList";
    }
}
