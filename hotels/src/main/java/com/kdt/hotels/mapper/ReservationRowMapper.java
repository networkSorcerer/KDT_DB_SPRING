package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReservationVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<ReservationVO> {
    @Override
    public ReservationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReservationVO(
                rs.getInt("reserveID"),
                rs.getString("userID"),
                rs.getInt("hotelID"),
                rs.getString("hotelName"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getInt("roomid"),
                rs.getString("roomtype"),
                rs.getInt("price"),
                rs.getInt("roomNumber")
        );
    }
}
