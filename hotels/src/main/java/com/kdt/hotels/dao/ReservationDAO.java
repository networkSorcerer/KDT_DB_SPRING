package com.kdt.hotels.dao;

import com.kdt.hotels.mapper.ReservationRowMapper;
import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저 예약 관리
    public List<ReservationVO> userReservationList(String userID){
        String sql = "SELECT RE.RESERVEID, RE.USERID, RE.HOTELID, HO.HOTELNAME, " +
                        "RE.STARTDATE, RE.ENDDATE, RE.ROOMID, RO.ROOMTYPE, RO.PRICE, RO.ROOMNUMBER " +
                        "FROM RESERVATION RE JOIN HOTEL HO ON RE.HOTELID = HO.HOTELID " +
                                            "JOIN ROOM RO ON RE.ROOMID = RO.ROOMID " +
                        "WHERE USERID = ?";
        return jdbcTemplate.query(sql, new ReservationRowMapper(), userID);
    }

    // 유저 예약 수정
    public boolean userReservationUpdate(ReservationVO vo){
        int result = 0;
        String sql = "UPDATE RESERVATION SET STARTDATE = ?, ENDDATE = ?, ROOMID = ? WHERE RESERVEID = ?";
        try{
            result = jdbcTemplate.update(sql, vo.getStartDate(), vo.getEndDate(), vo.getRoomID(), vo.getReserveID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    public boolean reserveHotel(ReservationVO vo) {
        int result = 0;
        // SQL 문 수정
        String sql = "INSERT INTO reservation (reserveid, userid, hotelid, roomid, startdate, enddate) VALUES (reservation_seq.nextval, ?, ?, ?, ?, ?)";
        try {
            // reserveID를 제거하고 나머지 매개변수만 사용
            result = jdbcTemplate.update(sql, vo.getUserID(), vo.getHotelID(), vo.getRoomID(), vo.getStartDate(), vo.getEndDate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    // 유저 예약 삭제
    public boolean userReservationDelete(int reserveID){
        int result = 0;
        String sql = "DELETE RESERVATION WHERE RESERVEID = ?";
        try {
            result = jdbcTemplate.update(sql, reserveID);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result > 0;
    }


    public List<ReservationVO> myReserveList(String userID) {
        String sql = "SELECT R.RESERVEID, rm.roomid,h.hotelid, R.USERID, H.HOTELNAME, RM.ROOMTYPE, RM.PRICE, RM.ROOMNUMBER, R.STARTDATE, R.ENDDATE " +
                "FROM RESERVATION R " +
                "JOIN HOTEL H ON R.HOTELID = H.HOTELID " +
                "JOIN ROOM RM ON R.ROOMID = RM.ROOMID " +
                "WHERE R.USERID = ?";
                //rowmapper에 10개의 열이 있으면 10개 다해야 함 아까 그래서 mypage는 안되고 내껀되고 그랬던거임
        return jdbcTemplate.query(sql, new ReservationRowMapper(), userID);

    }

}
