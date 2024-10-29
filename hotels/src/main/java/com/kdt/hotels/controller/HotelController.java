package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.ReviewVO;
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
    private final ReviewDAO reviewDAO;
    public HotelController(HotelDAO hotelDAO, RoomDAO roomDAO, ReviewDAO reviewDAO) {
        this.hotelDAO = hotelDAO;
        this.roomDAO = roomDAO;
        this.reviewDAO = reviewDAO;
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
        List<ReviewVO> hotelReview = reviewDAO.hotelReviewList(hotelId);
        model.addAttribute("avaRoom",avaRoom);
        model.addAttribute("hotelReview",hotelReview);
        return "/HotelList/selectRoom";
    }
}
