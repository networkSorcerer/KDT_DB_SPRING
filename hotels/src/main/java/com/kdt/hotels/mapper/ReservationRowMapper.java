package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReservationVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRowMapper implements RowMapper<ReservationVO> {
    @Override
    public ReservationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReservationVO(
                rs.getInt("RESERVEID"),
                rs.getString("USERID"),
                rs.getInt("HOTELID"),
                rs.getString("HOTELNAME"),
                rs.getDate("STARTDATE"),
                rs.getDate("ENDDATE"),
                rs.getInt("ROOMID"),
                rs.getString("ROOMTYPE"),
                rs.getInt("PRICE"),
                rs.getInt("ROOMNUMBER")
        );
    }
}
