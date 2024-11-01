package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReservationVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<ReservationVO> {
    @Override
    public ReservationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationVO reservation = new ReservationVO();
        reservation.setReserveID(rs.getInt("reserveID")); // "reserveid"가 정확해야 함
        reservation.setUserID(rs.getString("userID"));
        reservation.setHotelID(rs.getInt("hotelID"));
        reservation.setHotelName(rs.getString("hotelName"));
        reservation.setStartDate(rs.getDate("startDate"));
        reservation.setEndDate(rs.getDate("endDate"));
        reservation.setRoomID(rs.getInt("roomID"));
        reservation.setRoomType(rs.getString("roomType"));
        reservation.setPrice(rs.getInt("price"));
        reservation.setRoomNumber(rs.getInt("roomNumber")); // 여기서도 정확해야 함

        return reservation;
    }
}
