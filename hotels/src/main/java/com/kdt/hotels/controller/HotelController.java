package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelDAO hotelDAO;
    private final RoomDAO roomDAO;
    public HotelController(HotelDAO hotelDAO, RoomDAO roomDAO) {
        this.hotelDAO = hotelDAO;
        this.roomDAO = roomDAO;
    }
    @PostMapping ("/selectCity")
    public String selectCity(@RequestParam("region") String city, Model model) {
        List<HotelVO> cityHotels = hotelDAO.cityHotels(city);
        model.addAttribute("cityHotels",cityHotels);
        return "HotelList/cityHotels";
    }
    @GetMapping("/selectRoom")
    public String selectRoom(@RequestParam("hotelId") int hotelId,@RequestParam("hotelName") String hotelName,Model model){
        model.addAttribute("hotelId",hotelId);
        model.addAttribute("hotelName",hotelName);
        List<RoomVO> avaRoom = roomDAO.avaRoom(hotelId);
        model.addAttribute("avaRoom",avaRoom);
        return "/HotelList/reserveHotel";
    }
}
