package com.kdt.hotels.controller;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.HotelVO;
import com.kdt.hotels.vo.RoomVO;
import com.kdt.hotels.vo.UsersVO;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HotelDAO hotelDAO;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private RoomDAO roomDAO;

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
    @GetMapping("/management/hotel/add")
    public String showAddHotelPage(Model model) {
        model.addAttribute("hotel", new HotelVO());
        return "admin/add_hotel";
    }

    // 호텔 추가 처리
    @PostMapping("/management/hotel/add")
    public String addHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelInsert(hotel); // hotelId가 포함된 hotel 객체를 삽입
        return "redirect:/admin/management/hotel";
    }

    // 호텔 수정 페이지 이동
    @GetMapping("/management/hotel/update/{hotelID}")
    public String showUpdateHotelPage(@PathVariable("hotelID") String hotelID, Model model) {
        List<HotelVO> hotels = hotelDAO.findHotelById(hotelID);
        if (!hotels.isEmpty()) {
            model.addAttribute("hotel", hotels.get(0));
        }
        return "admin/update_hotel";
    }

    // 호텔 수정 처리
    @PostMapping("/management/hotel/update/{hotelID}")
    public String updateHotel(@ModelAttribute HotelVO hotel) {
        hotelDAO.hotelUpdate(hotel);
        return "redirect:/admin/management/hotel";
    }

    // 삭제 확인 페이지 이동
    @GetMapping("/management/hotel/delete/{hotelID}")
    public String showDeleteConfirmation(@PathVariable("hotelID") String hotelID, Model model) {
        List<HotelVO> hotel = hotelDAO.findHotelById(hotelID); // 호텔 정보를 가져오는 메소드
        model.addAttribute("hotel", hotel);
        return "admin/confirm_delete"; // 삭제 확인 페이지로 이동
    }

    // 실제 삭제 처리
    @PostMapping("/management/hotel/delete/{hotelID}")
    public String deleteHotel(@PathVariable("hotelID") String hotelID) {
        hotelDAO.hotelDelete(hotelID); // 호텔 삭제 메소드
        return "redirect:/admin/management/hotel"; // 관리 페이지로 리다이렉트
    }
    // 호텔관리 리스트에서 방관리 리스트로
    @GetMapping("/management/hotel/room/select")
    public String showRoomList(@RequestParam("hotelID") int hotelID, Model model) {
        System.out.println("Hotel ID received: " + hotelID);

        List<RoomVO> rooms = roomDAO.hotelRoom(hotelID);
        System.out.println("Rooms found: " + rooms.size());

        model.addAttribute("rooms", rooms);
        model.addAttribute("hotelID", hotelID);
        return "admin/room_management";
    }
    // 방 추가 페이지 이동
    @GetMapping("/management/hotel/room/add")
    public String showAddRoomPage(@RequestParam("hotelID") int hotelID, Model model) {
        RoomVO room = new RoomVO();
        room.setHotelID(hotelID);
        model.addAttribute("room", room);
        model.addAttribute("hotelID", hotelID); //취소 버튼에 필요
        return "admin/add_room";
    }
    @PostMapping("/management/hotel/room/add")
    public String addRoom(@ModelAttribute RoomVO room) {
        roomDAO.roomPlus(room);
        return "redirect:/admin/management/hotel/room/select?hotelID=" + room.getHotelID();
    }

    // 방 수정 페이지 이동
    @GetMapping("/management/hotel/room/update/{roomID}")
    public String showUpdateRoomPage(@PathVariable("roomID") int roomID, @RequestParam("hotelID") int hotelID, Model model) {
        RoomVO room = roomDAO.selectRoom(roomID).get(0);
        model.addAttribute("room", room);
        model.addAttribute("hotelID", hotelID); // 돌아가기 버튼에 필요
        return "admin/update_room";
    }

    @PostMapping("/management/hotel/room/update/{roomID}")
    public String updateRoom(@ModelAttribute RoomVO room, @RequestParam("hotelID") int hotelID, Model model) {
        System.out.println("Updating Room: " + room);  // RoomVO의 toString() 메소드를 오버라이드 하거나, 각 필드 출력
        roomDAO.roomUpdate(room);
        model.addAttribute("hotelID", hotelID);
        return "redirect:/admin/management/hotel/room/select?hotelID=" + hotelID;
    }

    // 방 삭제 처리
    @PostMapping("/management/hotel/room/delete/{roomID}")
    public String deleteRoom(@PathVariable("roomID") int roomID, @RequestParam("hotelID") int hotelID, Model model) {
        roomDAO.roomDelete(roomID); // 방 삭제 메소드
        model.addAttribute("hotelID", hotelID);
        return "redirect:/admin/management/hotel/room/select?hotelID=" + hotelID;
    }



    // 유저 관리 페이지
    @GetMapping("/management/user")
    public String showUserManagementPage(Model model) {
        List<UsersVO> users = usersDAO.usersSelect();
        model.addAttribute("users", users);
        return "admin/user_management";
    }

    // 유저 삭제 처리
    @PostMapping("management/user/delete/{userID}")
    public String deleteUser(@PathVariable("userID") String userID) {
        try {
            usersDAO.userDelete(userID);
            return "redirect:/admin/management/user"; // 유저 관리 페이지로 리다이렉트
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return "redirect:/admin/management/user"; // 오류 발생 시에도 페이지 리디렉션
        }
    }

    // 유저 등급 수정 처리 (유저를 관리자 등급으로)
    @PostMapping("/management/user/promote/{userID}")
    public String promoteUser(@PathVariable("userID") String userID) {
        UsersVO user = new UsersVO();
        user.setUserID(userID);
        user.setGrade(1);  // 관리자로 승격
        usersDAO.userToManagerUpdate(user);
        return "redirect:/admin/management/user";
    }
    // 유저 등급 강등 처리 (관리자를 일반 유저 등급으로)
    @PostMapping("/management/user/demote/{userID}")
    public String demoteUser(@PathVariable("userID") String userID) {
        UsersVO user = new UsersVO();
        user.setUserID(userID);
        user.setGrade(0);  // 일반 유저로 강등
        usersDAO.userToManagerUpdate(user);
        return "redirect:/admin/management/user";
    }

    // 관리자 유저 추가 페이지 이동
    @GetMapping("/management/user/add")
    public String showAddAdminUserPage(Model model) {
        model.addAttribute("user", new UsersVO());
        return "admin/add_user"; // 이 경로가 맞는지 확인
    }

    // 관리자 유저 추가 처리
    @PostMapping("management/user/add")
    public String addAdminUser(@ModelAttribute UsersVO user, Model model) {
        user.setGrade(1); // 기본 등급을 1(관리자)로 설정

        if (usersDAO.checkUserIDExists(user.getUserID())) { // 중복 확인
            model.addAttribute("errorMessage", "아이디가 중복됩니다. 다른 아이디를 입력해 주세요.");
            return "admin/add_user"; // 중복 아이디일 경우 다시 회원가입 페이지로 이동
        }
        if (usersDAO.ManagerInsert(user)) {
            model.addAttribute("successMessage", "회원가입 성공!");
            return "redirect:/admin/management/user"; // 성공 시 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "회원가입 실패");
            return "admin/add_user"; // 실패 시 다시 회원가입 페이지로 이동
        }
    }
    // 간단한 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 메인 페이지로 리다이렉트
    }
}