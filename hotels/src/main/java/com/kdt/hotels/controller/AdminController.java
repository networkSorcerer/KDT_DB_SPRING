package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.vo.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HotelDAO hotelDAO;

    // 호텔 관리 페이지
    @GetMapping("/hotel_management")
    public String showHotelManagementPage(Model model) {
        List<HotelVO> hotels = hotelDAO.hotelSelect();  // 모든 호텔 목록 조회
        model.addAttribute("hotels", hotels);
        return "admin/hotel_management";  // 해당 HTML 파일과 매핑
    }

    // 호텔 추가 페이지 이동
    @GetMapping("/hotel/add")
    public String showAddHotelPage(Model model) {
        model.addAttribute("hotel", new HotelVO());
        return "admin/add_hotel";  // 추가 페이지로 이동
    }

    // 호텔 추가 처리
    @PostMapping("/hotel/add")
    public String addHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelInsert(hotel);
        return "redirect:/admin/hotel_management";
    }

    // 호텔 수정 페이지 이동
    @GetMapping("/hotel/update/{hotelID}")
    public String showUpdateHotelPage(@PathVariable("hotelID") String hotelID, Model model) {
        List<HotelVO> hotels = hotelDAO.findHotelById(hotelID);
        if (!hotels.isEmpty()) {
            model.addAttribute("hotel", hotels.get(0));
        }
        return "admin/update_hotel";  // 수정 페이지로 이동
    }

    @PostMapping("/admin/hotel/update")
    public String updateHotel(HotelVO hotel) {
        hotelDAO.hotelUpdate(hotel);
        return "redirect:/admin/hotel_management"; // 업데이트 후 리다이렉트
    }

    @PostMapping("/admin/hotel/delete/{id}")
    public String deleteHotel(@PathVariable("id") int id) {
        hotelDAO.hotelDelete(String.valueOf(id));
        return "redirect:/admin/hotel_management"; // 삭제 후 리다이렉트
    }
}