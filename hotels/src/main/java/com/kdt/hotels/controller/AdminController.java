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

    @Autowired
    private HotelDAO hotelDAO;

    @Autowired
    private UsersDAO usersDAO;

    // 관리자 메인 페이지
    @GetMapping("/management")
    public String adminMainPage() {
        return "admin/management";
    }

    // 호텔 관리 페이지
    @GetMapping("/hotel_management")
    public String showHotelManagementPage(Model model) {
        List<HotelVO> hotels = hotelDAO.hotelSelect();
        model.addAttribute("hotels", hotels);
        return "admin/hotel_management";
    }

    // 호텔 추가 페이지 이동
    @GetMapping("/hotel/add")
    public String showAddHotelPage(Model model) {
        model.addAttribute("hotel", new HotelVO());
        return "admin/add_hotel";
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
        return "admin/update_hotel";
    }

    // 호텔 수정 처리
    @PostMapping("/hotel/update")
    public String updateHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelUpdate(hotel);
        return "redirect:/admin/hotel_management";
    }

    // 호텔 삭제 처리
    @PostMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable("id") int id) {
        hotelDAO.hotelDelete(String.valueOf(id));
        return "redirect:/admin/hotel_management";
    }

    // 유저 관리 페이지
    @GetMapping("/user_management")
    public String showUserManagementPage(Model model) {
        List<UsersVO> users = usersDAO.usersSelect();
        model.addAttribute("users", users);
        return "admin/user_management";
    }

    // 유저 삭제 처리
    @PostMapping("/user/delete/{userID}")
    public String deleteUser(@PathVariable("userID") String userID) {
        usersDAO.userDelete(userID);
        return "redirect:/admin/user_management";
    }

    // 유저 등급 수정 처리 (유저를 관리자 등급으로)
    @PostMapping("/user/promote/{userID}")
    public String promoteUser(@PathVariable("userID") String userID) {
        UsersVO user = new UsersVO();
        user.setUserID(userID);
        user.setGrade(1);  // 관리자로 승격
        usersDAO.userToManagerUpdate(user);
        return "redirect:/admin/user_management";
    }

    // 관리자 유저 추가 페이지 이동
    @GetMapping("/user/add")
    public String showAddAdminUserPage(Model model) {
        model.addAttribute("user", new UsersVO());
        return "admin/add_user";
    }

    // 관리자 유저 추가 처리
    @PostMapping("/user/add")
    public String addAdminUser(@ModelAttribute UsersVO user) {
        user.setGrade(1);  // 관리자 권한 설정
        usersDAO.ManagerInsert(user);
        return "redirect:/admin/user_management";
    }
}