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
        model.addAttribute("hotelid",hotelID);
        return "/HotelList/RoomList"; // 뷰 경로가 올바른지 확인하세요.
    }
    @GetMapping("/select")
    public String roomSelect(Model model, @RequestParam(value = "roomid", required = false) Integer roomid,
                             @RequestParam(value="hotelid", required = false)Integer hotelid) {
        System.out.println("호텔 ID야 나타나라: " + hotelid);
        System.out.println("room ID야 나타나라: " + roomid);

        if (roomid == null) {
            model.addAttribute("hotelid",hotelid);
            return "HotelList/updateRoom"; // 방 추가 페이지 반환

        } else {
            // roomid가 있을 때는 방 수정 정보 조회
            List<RoomVO> selectRooms = roomDAO.selectRoom(roomid);
            RoomVO selectRoom = (selectRooms.isEmpty())? null : selectRooms.get(0);
            model.addAttribute("selectRoom", selectRoom);
            return "HotelList/updateRoom"; // 방 수정 페이지 반환
        }
    }
    @PostMapping("/update")
    public String roomUpdate(@ModelAttribute RoomVO vo, Model model) {
        boolean isSuccess; // 변수 선언

        if (vo.getRoomID() != null) { // null 체크를 올바르게 수정
            isSuccess = roomDAO.roomUpdate(vo); // 방 수정
        } else {
            isSuccess = roomDAO.roomPlus(vo); // 방 추가
        }

        model.addAttribute("isSuccess", isSuccess);
        return "HotelList/roomResult"; // 업데이트 결과 페이지 반환
    }


    @GetMapping("/delete")
    public boolean roomDelete(Model model){

       return false;
   }



}
