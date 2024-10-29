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
    @GetMapping("/management/hotel")
    public String showHotelManagementPage(Model model) {
        List<HotelVO> hotels = hotelDAO.hotelSelect();
        model.addAttribute("hotels", hotels);
        return "admin/hotel_management";
    }

    // 호텔 추가 페이지 이동
    @GetMapping("management/hotel/add")
    public String showAddHotelPage(Model model) {
        model.addAttribute("hotel", new HotelVO());
        return "admin/add_hotel";
    }

    // 호텔 추가 처리
    @PostMapping("management/hotel/add")
    public String addHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelInsert(hotel); // hotelId가 포함된 hotel 객체를 삽입
        return "redirect:/admin/management/hotel";
    }

    // 호텔 수정 페이지 이동
    @GetMapping("management/hotel/update/{hotelID}")
    public String showUpdateHotelPage(@PathVariable("hotelID") String hotelID, Model model) {
        List<HotelVO> hotels = hotelDAO.findHotelById(hotelID);
        if (!hotels.isEmpty()) {
            model.addAttribute("hotel", hotels.get(0));
        }
        return "admin/update_hotel";
    }

    // 호텔 수정 처리
    @PostMapping("management/hotel/update/{hotelID}")
    public String updateHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelUpdate(hotel);
        return "redirect:/admin/management/hotel";
    }

    @GetMapping("management/hotel/delete/{hotelID}")
    public String showDeleteConfirmation(@PathVariable("hotelID") String hotelID, Model model) {
        List<HotelVO> hotel = hotelDAO.findHotelById(hotelID); // 호텔 정보를 가져오는 메소드
        model.addAttribute("hotel", hotel);
        return "admin/confirm_delete"; //
    }

    @PostMapping("management/hotel/delete/{hotelID}")
    public String deleteHotel(@PathVariable("hotelID") String hotelID) {
        hotelDAO.hotelDelete(hotelID); // 호텔 삭제 메소드
        return "redirect:/admin/management/hotel"; // 관리 페이지로 리다이렉트
    }

    // 유저 관리 페이지
    @GetMapping("management/user")
    public String showUserManagementPage(Model model) {
        List<UsersVO> users = usersDAO.usersSelect();
        model.addAttribute("users", users);
        return "admin/user_management";
    }

    // 유저 삭제 처리
    @PostMapping("management/user/delete/{userID}")
    public String deleteUser(@PathVariable("userID") String userID) {
        usersDAO.userDelete(userID);
        return "redirect:/admin/user_management";
    }

    // 유저 등급 수정 처리 (유저를 관리자 등급으로)
    @PostMapping("management/user/promote/{userID}")
    public String promoteUser(@PathVariable("userID") String userID) {
        UsersVO user = new UsersVO();
        user.setUserID(userID);
        user.setGrade(1);  // 관리자로 승격
        usersDAO.userToManagerUpdate(user);
        return "redirect:/admin/user_management";
    }

    // 관리자 유저 추가 페이지 이동
    @GetMapping("management/user/add")
    public String showAddAdminUserPage(Model model) {
        model.addAttribute("user", new UsersVO());
        return "admin/add_user";
    }

    // 관리자 유저 추가 처리
    @PostMapping("management/user/add")
    public String addAdminUser(@ModelAttribute UsersVO user) {
        user.setGrade(1);  // 관리자 권한 설정
        usersDAO.ManagerInsert(user);
        return "redirect:/admin/user_management";
    }
}