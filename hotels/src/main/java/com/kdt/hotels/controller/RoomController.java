package com.kdt.hotels.controller;

import com.kdt.hotels.dao.RoomDAO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/select")
    public String roomSelect(Model model, @RequestParam(value = "roomid", required = false) Integer roomid) {
        if (roomid == null) {
            // roomid가 null일 때는 방 추가(create) 페이지로 리다이렉트
            return "HotelList/updateRoom"; // 방 추가 페이지 반환
        } else {
            // roomid가 있을 때는 방 수정 정보 조회
            List<RoomVO> selectRooms = roomDAO.selectRoom(roomid);
            RoomVO selectRoom = (selectRooms.isEmpty())? null : selectRooms.get(0);
            model.addAttribute("selectRoom", selectRoom);
            return "HotelList/updateRoom"; // 방 수정 페이지 반환
        }
    }
    @GetMapping("/update")
    public String roomUpdate(@ModelAttribute RoomVO vo){
        roomDAO.roomUpdate(vo);

        return "HotelList/roomResult";
    }
    @GetMapping("/delete")
    public boolean roomDelete(Model model){

       return false;
   }

}
