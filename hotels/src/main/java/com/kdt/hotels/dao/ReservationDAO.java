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

    public List<ReservationVO> userReservationList(){
        String sql = "SELECT RE.RESERVEID, RE.USERID, RE.HOTELID, HO.HOTELNAME, " +
                        "RE.STARTDATE, RE.ENDDATE, RE.ROOMID, RO.ROOMTYPE, RO.PRICE " +
                        "FROM RESERVATION RE JOIN HOTEL HO " +
                                            "ON RE.HOTELID = HO.HOTELID " +
                                            "JOIN ROOM RO " +
                                            "ON RE.ROOMID = RO.ROOMID";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }
}
