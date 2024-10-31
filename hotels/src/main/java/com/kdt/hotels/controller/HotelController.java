package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.ReviewDAO;
import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.ReviewVO;
import com.kdt.hotels.vo.RoomVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelDAO hotelDAO;
    private final RoomDAO roomDAO;
    private final ReviewDAO reviewDAO;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    public String selectRoom(@RequestParam("hotelId") int hotelId, @RequestParam("hotelName") String hotelName, Model model, HttpSession session){
        model.addAttribute("hotelId",hotelId);
        model.addAttribute("hotelName",hotelName);
        session.setAttribute("hotelId",hotelId);
        String userid = (String) session.getAttribute("userid");
        session.setAttribute("hotelName",hotelName );
        logger.info("userid = {}",userid);
        List<RoomVO> avaRoom = roomDAO.avaRoom(hotelId);
        List<ReviewVO> hotelReview = reviewDAO.hotelReviewList(hotelId);
        model.addAttribute("avaRoom",avaRoom);
        model.addAttribute("hotelReview",hotelReview);
        model.addAttribute("userid",userid);
        model.addAttribute("hotelId",hotelId);
        return "/HotelList/selectRoom";
    }
    @GetMapping ("/hotelList")
    public String hotelList(Model model) {
        List<HotelVO> hotelList =hotelDAO.hotelList4();
        model.addAttribute("hotelList",hotelList);
        return "HotelList/Hotels";
    }
}
