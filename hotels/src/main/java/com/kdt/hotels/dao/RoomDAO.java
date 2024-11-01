package com.kdt.hotels.dao;

import com.kdt.hotels.vo.ReservationVO;
import com.kdt.hotels.vo.RoomVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<RoomVO> hotelRoom(int hotelId){
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

        String sql = "UPDATE room SET roomtype = ?, price = ?, roomnumber = ? WHERE roomid = ?";
        try {

            result = jdbcTemplate.update(sql, vo.getRoomType(), vo.getPrice(), vo.getRoomNumber(), vo.getRoomID());

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

    public List<RoomVO> chooseRoom(ReservationVO reservation) {
        String sql = "SELECT r.roomID, r.hotelID, r.price,r.roomType," +
                "r.roomNumber " +
                "FROM room r " +
                "WHERE r.hotelID = ? " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM reservation v " +
                "    WHERE v.roomid = r.roomid " +
                "    AND ( " +
                "        (v.startDate < ? AND " +
                "         v.endDate > ?) " +
                "    ) " +
                ")";


        List<RoomVO> availableRooms = new ArrayList<>();
        System.out.println(reservation.getHotelID());
        System.out.println(reservation.getStartDate());
        System.out.println(reservation.getEndDate());
        try {
            System.out.println("SQL: " + sql);
            System.out.println("Parameters: " + reservation.getHotelID() + ", " + reservation.getEndDate() + ", " + reservation.getStartDate());

            availableRooms = jdbcTemplate.query(sql, new Object[]{
                    reservation.getHotelID(),
                    Date.valueOf(reservation.getEndDate().toLocalDate()),  // String -> Date로 변환
                    Date.valueOf(reservation.getStartDate().toLocalDate()) // String -> Date로 변환
            }, new RoomRowMapper() {
                @Override
                public RoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RoomVO room = new RoomVO();
                    room.setRoomID(rs.getInt("roomID"));
                    room.setHotelID(rs.getInt("hotelID"));
                    room.setRoomType(rs.getString("roomType"));
                    room.setPrice(rs.getInt("price"));
                    room.setRoomNumber(rs.getInt("roomNumber"));
                    return room;
                }
            });
        } catch (DataAccessException e) {
            System.err.println("쿼리 실행 중 오류: " + e.getMessage());
            if (e.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getCause();
                System.err.println("SQL State: " + sqlException.getSQLState());
                System.err.println("Error Code: " + sqlException.getErrorCode());
            }
        }

        return availableRooms;
    }
    public void roomDelete(int roomID) {
        String sql = "DELETE ROOM WHERE ROOMID = ? ";
        try {
            jdbcTemplate.update(sql, roomID);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
