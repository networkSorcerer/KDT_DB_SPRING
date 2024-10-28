package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.vo.HotelVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelDAO hotelDAO;

    public HotelController(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }
    @PostMapping ("/selectCity")
    public String selectCity(@RequestParam("region") String city, Model model) {
        List<HotelVO> cityHotels = hotelDAO.cityHotels(city);
        model.addAttribute("cityHotels",cityHotels);
        return "HotelList/cityHotels";
    }
}
