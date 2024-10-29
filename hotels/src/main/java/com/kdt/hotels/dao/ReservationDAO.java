package com.kdt.hotels.dao;

import com.kdt.hotels.mapper.ReservationRowMapper;
import com.kdt.hotels.vo.ReservationVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저 예약 관리
    public List<ReservationVO> userReservationList(){
        String sql = "SELECT RE.RESERVEID , RE.USERID , RE.HOTELID , HO.HOTELNAME , " +
                        "RE.STARTDATE , RE.ENDDATE , RE.ROOMID , RO.ROOMTYPE , RO.PRICE, RO.ROOMNUMBER " +
                        "FROM RESERVATION RE JOIN HOTEL HO " +
                                            "ON RE.HOTELID = HO.HOTELID " +
                                            "JOIN ROOM RO " +
                                            "ON RE.ROOMID = RO.ROOMID";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    // 유저 예약 수정
    public void userReservationUpdate(ReservationVO vo){
        String sql = "UPDATE RESERVATION SET STARTDATE = ?, ENDDATE = ?, ROOMID = ? WHERE RESERVEID = ?";
        jdbcTemplate.update(sql, vo.getStartDate(), vo.getEndDate(), vo.getRoomid(), vo.getReserveID());
    }

    public boolean reserveHotel(ReservationVO vo) {
        int result = 0;
        String sql = "insert into reservation (reserveid, userid,  hotelid, roomid,startdate, enddate ) values (?,?,?,?,?,?)";
        try {
            result = jdbcTemplate.update(sql, vo.getReserveID(),vo.getUserID(),vo.getHotelID(),vo.getRoomid(),vo.getStartDate(),vo.getEndDate());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
}
