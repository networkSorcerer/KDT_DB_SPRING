package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HotelDAO hotelDAO;
    private final UsersDAO usersDAO;

    @Autowired
    public AdminController(HotelDAO hotelDAO, UsersDAO usersDAO) {
        this.hotelDAO = hotelDAO;
        this.usersDAO = usersDAO;
    }

    @GetMapping
    public String adminPage(Model model) {
        List<HotelVO> hotels = hotelDAO.hotelSelect();
        List<UsersVO> users = usersDAO.usersSelect();
        model.addAttribute("hotels", hotels);
        model.addAttribute("users", users);
        return "admin";
    }

    // 호텔 삭제
    @PostMapping("/deleteHotel")
    public String deleteHotel(@RequestParam("hotelID") String hotelID) {
        hotelDAO.hotelDelete(hotelID);
        return "redirect:/admin";
    }

    // 사용자 삭제
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userID") String userID) {
        usersDAO.userDelete(userID);
        return "redirect:/admin";
    }

    // 호텔 수정
    @GetMapping("/editHotel/{hotelID}")
    public String editHotelForm(@PathVariable("hotelID") String hotelID, Model model) {
        HotelVO hotel = (HotelVO) hotelDAO.findHotelById(hotelID); // 추가한 findHotelById 메서드 사용
        model.addAttribute("hotel", hotel);
        return "editHotel";
    }

    @PostMapping("/editHotel")
    public String editHotelSubmit(HotelVO hotel) {
        hotelDAO.hotelUpdate(hotel);
        return "redirect:/admin";
    }

    // 사용자 수정
    @GetMapping("/editUser/{userID}")
    public String editUserForm(@PathVariable("userID") String userID, Model model) {
        UsersVO user = (UsersVO) usersDAO.findUserById(userID); // 추가한 findUserById 메서드 사용
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String editUserSubmit(UsersVO user) {
        usersDAO.userUpdate(user);
        return "redirect:/admin";
    }
}