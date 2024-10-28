package com.kdt.hotels.dao;

import com.kdt.hotels.vo.HotelVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HotelDAO {
    public final JdbcTemplate jdbcTemplate;

    public HotelDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<HotelVO> hotelSelect() {
        String sql = "SELECT * FROM HOTEL";
        return jdbcTemplate.query(sql, new HotelRowMapper());
    }

    public boolean hotelInsert(HotelVO hotel) {
        int result = 0;
        String sql = "INSERT INTO HOTEL (HOTLEID, HOTELNAME, REGION, PHONE, HOTELEXPL) "+
                "VALUES (?, ?, ?, ?, ?)";
        try {
            result = jdbcTemplate.update(sql, hotel.getHotelID(),hotel.getHotelName(),hotel.getRegion(),hotel.getPhone(),hotel.getHotelExpl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
    public boolean hotelDelete(String hotelID) {
        int result = 0;
        String sql = "DELETE FROM HOTEL WHERE HOTELID = ?";
        try {
            result = jdbcTemplate.update(sql, hotelID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    public boolean hotelUpdate(HotelVO hotel) {
        int result = 0;
        String sql = "UPDATE HOTEL SET HOTELNAME = ?, REGION = ?, PHONE = ? HOTELEXPL = ? WHERE HOTELID =?";
        try {
            result = jdbcTemplate.update(sql, hotel.getHotelName(), hotel.getRegion(), hotel.getPhone(), hotel.getHotelExpl(), hotel.getHotelID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
    public void hotelUserRst(List<HotelVO> list) {
        System.out.println("--------------------");
        System.out.println("     호텔 리스트");
        System.out.println("--------------------");
        for (HotelVO e : list) {
            System.out.print(e.getHotelName() + " ");
            System.out.print(e.getRegion() +  " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getHotelExpl());
        }
    }
    public void hotelManagerRst(List<HotelVO> list) {
        System.out.println("--------------------");
        System.out.println("  호텔 리스트(관리자)");
        System.out.println("--------------------");
        for (HotelVO e : list) {
            System.out.print(e.getHotelID() + " ");
            System.out.print(e.getHotelName() + " ");
            System.out.print(e.getRegion() + " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getHotelExpl());
        }
    }


    public static class HotelRowMapper implements RowMapper<HotelVO> {
        @Override
        public HotelVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new HotelVO(
                    rs.getInt("HOTELID"),
                    rs.getString("HOTELNAME"),
                    rs.getString("REGION"),
                    rs.getString("PHONE"),
                    rs.getString("HOTELEXPL")
            );
        }
    }
}
