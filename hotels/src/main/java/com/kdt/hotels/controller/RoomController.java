package com.kdt.hotels.controller;

import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomDAO roomDAO;

    @GetMapping("/hotelRoom/{hotelID}")
    public String hotelRoom(@PathVariable("hotelID") int hotelID, Model model) {

        List<RoomVO> hotelRoom = roomDAO.hotelRoom(hotelID);

        model.addAttribute("hotelRoom", hotelRoom);

        return "/HotelList/RoomList"; // 뷰 경로가 올바른지 확인하세요.
    }
}
