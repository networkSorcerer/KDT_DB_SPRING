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
    public String roomSelect(Model model, @RequestParam(value = "roomID", required = false) Integer roomID,
                             @RequestParam(value="hotelID", required = false)Integer hotelID) {

        if (roomID == null) {
            model.addAttribute("hotelID",hotelID);
            return "HotelList/updateRoom"; // 방 추가 페이지 반환

        } else {
            // roomid가 있을 때는 방 수정 정보 조회
            List<RoomVO> selectRooms = roomDAO.selectRoom(roomID);
            //List<RoomVO> roomType =
            RoomVO selectRoom = (selectRooms.isEmpty())? null : selectRooms.get(0);
            model.addAttribute("selectRoom", selectRoom);
            return "HotelList/updateRoom"; // 방 수정 페이지 반환
        }
    }
    @PostMapping("/update")
    public String roomUpdate(@ModelAttribute RoomVO vo, Model model,
                             @RequestParam(value = "roomID", required = false) Integer roomID,
                             @RequestParam("hotelID")int hotelID,
                             @RequestParam("roomType")String roomType,
                             @RequestParam("price") int price,
                             @RequestParam("roomNumber")int roomNumber
    ) {
        boolean isSuccess; // 변수 선언

        RoomVO roomVO = new RoomVO();
        roomVO.setRoomID(roomID);
        roomVO.setHotelID(hotelID);
        roomVO.setRoomType(roomType);
        roomVO.setPrice(price);
        roomVO.setRoomNumber(roomNumber);
        if (roomID != null) { // null 체크를 올바르게 수정
            isSuccess = roomDAO.roomUpdate(roomVO); // 방 수정
        } else {
            isSuccess = roomDAO.roomPlus(roomVO); // 방 추가
        }

        model.addAttribute("isSuccess", isSuccess);
        return "HotelList/roomResult"; // 업데이트 결과 페이지 반환
    }


    @GetMapping("/delete")
    public boolean roomDelete(Model model){

       return false;
   }



}
