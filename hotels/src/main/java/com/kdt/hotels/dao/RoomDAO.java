package com.kdt.hotels.dao;

import com.kdt.hotels.vo.RoomVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomDAO {
    public final JdbcTemplate jdbcTemplate;


    public RoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RoomVO> avaRoom(int hotelId) {
        String sql = "select * from room where hotelid = ? ";

        return jdbcTemplate.query(sql, new RoomRowMapper(),hotelId);
    }
    public List<RoomVO>hotelRoom(int hotelId){
        String sql ="select * from room where hotelid = ?";
        try{
            return jdbcTemplate.query(sql, new RoomRowMapper(), hotelId);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RoomVO> selectRoom(int roomid) {
        String sql="select * from room where roomid = ?";
        try{
            return jdbcTemplate.query(sql, new RoomRowMapper(),roomid );
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean roomUpdate(RoomVO vo) {
        int result = 0;
        String sql = "UPDATE room SET hotelid = ?, roomtype = ?, price = ?, roomnumber = ? WHERE roomid = ?";
        try {
            System.out.println("Updating room with ID: " + vo.getRoomID());
            result = jdbcTemplate.update(sql, vo.getHotelID(), vo.getRoomType(), vo.getPrice(), vo.getRoomNumber(), vo.getRoomID());
            System.out.println("Rows affected: " + result);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result > 0;
    }


    public boolean roomPlus(RoomVO vo) {
        int result = 0;
        String sql = "INSERT INTO room (roomid, hotelid, roomtype, price, roomnumber) VALUES (room_seq.nextval, ?, ?, ?, ?)"; // "VALUES"로 수정
        try {
            result = jdbcTemplate.update(sql, vo.getHotelID(), vo.getRoomType(), vo.getPrice(), vo.getRoomNumber()); // roomType 중복 수정
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result > 0;
    }



    private static class RoomRowMapper implements RowMapper<RoomVO> {
        @Override
        public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new RoomVO(
                    rs.getInt("roomID"),
                    rs.getInt("hotelID"),
                    rs.getString("roomType"),
                    rs.getInt("price"),
                    rs.getInt("roomNumber")
            );
        }
    }

    public void roomList(List<RoomVO>list){
        System.out.println("-".repeat(20));
        System.out.println("          방 정보");
        System.out.println("-".repeat(20));

        for(RoomVO e : list) {
            System.out.println(e.getRoomID());
            System.out.println(e.getHotelID());
            System.out.println(e.getRoomType());
            System.out.println(e.getPrice());
            System.out.println(e.getRoomNumber());
        }
        System.out.println("-".repeat(20));
    }
}
