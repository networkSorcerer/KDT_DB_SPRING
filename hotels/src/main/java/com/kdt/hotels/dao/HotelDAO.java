package com.kdt.hotels.dao;

import com.kdt.hotels.vo.HotelStarVO;
import com.kdt.hotels.vo.HotelVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class HotelDAO {
    public final JdbcTemplate jdbcTemplate;

    public HotelDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<HotelVO> hotelSelect() {    //호텔의 모든 정보 뽑아오기(ID순 정렬)
        String sql = "SELECT * FROM HOTEL ORDER BY HOTELID";
        try {
            return jdbcTemplate.query(sql, new HotelRowMapper());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
    public void hotelList() {   //유저용 호텔 리스트
        List<HotelVO> list = hotelSelect();
        hotelUserRst(list);
    }

    public void allHotelList() {   //유저용 호텔 리스트+호텔 ID= 관리자용
        List<HotelVO> list = hotelSelect();
        hotelManagerRst(list);
    }
    public void hotelStarList() {   //유저용 호텔 리스트 (평균 별점순)
        List<HotelStarVO> list = hotelSelectStar();
        hotelUserStar(list);
    }


    public List<HotelStarVO> hotelSelectStar() {    //평균 별점을 뽑아와 그 순으로 나열
        String sql = "SELECT H.HOTELID AS ID , H.HOTELNAME AS name,h.REGION,h.PHONE,h.HOTELEXPL, AVG(r.STAR) AS AVG" +
                "FROM HOTEL h" +
                "JOIN REVIEWS r" +
                "ON H.HOTELID = R.HOTELID" +
                "GROUP BY H.HOTELID, H.HOTELNAME,h.REGION,h.PHONE,h.HOTELEXPL" +
                "ORDER BY AVG(r.STAR) DESC, H.HOTELNAME";
        try {
            return jdbcTemplate.query(sql, new HotelStarRowMapper());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }


    public List<HotelVO> findHotelById(String hotelID) {  // 호텔 아이디로 호텔 정보 찾기
        String sql = "SELECT * FROM HOTEL WHERE HOTELID = ?";
        try {
            return jdbcTemplate.query(sql, new HotelRowMapper(), hotelID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Transactional
    public boolean hotelInsert(HotelVO hotel) {
        int result = 0;
        String sql = "INSERT INTO HOTEL (HOTLEID, HOTELNAME, REGION, PHONE, HOTELEXPL) "+
                "VALUES (?, ?, ?, ?, ?)";
        try {
            result = jdbcTemplate.update(sql, hotel.getHotelID(),hotel.getHotelName(),hotel.getRegion(),hotel.getPhone(),hotel.getHotelExpl());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return result > 0;
    }
    @Transactional
    public boolean hotelDelete(String hotelID) {
        int result = 0;
        String sql = "DELETE FROM HOTEL WHERE HOTELID = ?";
        try {
            result = jdbcTemplate.update(sql, hotelID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return result > 0;
    }
    @Transactional
    public boolean hotelUpdate(HotelVO hotel) {
        int result = 0;
        String sql = "UPDATE HOTEL SET HOTELNAME = ?, REGION = ?, PHONE = ?, HOTELEXPL = ? WHERE HOTELID =?";
        try {
            result = jdbcTemplate.update(sql, hotel.getHotelName(), hotel.getRegion(), hotel.getPhone(), hotel.getHotelExpl(), hotel.getHotelID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return result > 0;
    }
    public void hotelUserRst(List<HotelVO> list) {
        System.out.println("--------------------");
        System.out.println("     호텔 정보");
        System.out.println("--------------------");
        for (HotelVO e : list) {
            System.out.print(e.getHotelName() + " ");
            System.out.print(e.getRegion() +  " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getHotelExpl());
            System.out.println();
        }
    }
    public void hotelUserStar(List<HotelStarVO> list) {
        System.out.println("--------------------");
        System.out.println("     호텔 정보");
        System.out.println("--------------------");
        for (HotelStarVO e : list) {
            System.out.print(e.getHotelName() + " ");
            System.out.print(e.getRegion() +  " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getHotelExpl()+ " ");
            System.out.print(e.getAvg_star());
            System.out.println();
        }
    }
    public void hotelManagerRst(List<HotelVO> list) {
        System.out.println("--------------------");
        System.out.println("  호텔 정보(관리자)");
        System.out.println("--------------------");
        for (HotelVO e : list) {
            System.out.print(e.getHotelID() + " ");
            System.out.print(e.getHotelName() + " ");
            System.out.print(e.getRegion() + " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getHotelExpl());
            System.out.println();
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
    public static class HotelStarRowMapper implements RowMapper<HotelStarVO> {
        @Override
        public HotelStarVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new HotelStarVO(
                    rs.getInt("HOTELID"),
                    rs.getString("HOTELNAME"),
                    rs.getString("REGION"),
                    rs.getString("PHONE"),
                    rs.getString("HOTELEXPL"),
                    rs.getDouble("AVG")
            );
        }


    }
}
