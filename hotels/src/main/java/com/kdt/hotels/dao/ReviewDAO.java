package com.kdt.hotels.dao;

import com.kdt.hotels.mapper.Review1RowMapper;
import com.kdt.hotels.mapper.ReviewRowMapper;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReviewDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저 리뷰 리스트
    public List<ReviewVO> userReviewList(String userID){
        String sql = "SELECT R.REVIEWID, R.HOTELID, H.HOTELNAME, R.USERID, R.CONTENT, R.STAR " +
                    "FROM REVIEWS R JOIN HOTEL H ON R.HOTELID = H.HOTELID " +
                    "WHERE USERID = ?";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), userID);
    }

    // 유저 리뷰 호텔별 리스트
    public List<ReviewVO> userReviewReserveList(String userID, int hotelID){
        String sql = "SELECT R.REVIEWID, R.HOTELID, H.HOTELNAME, R.USERID, R.CONTENT, R.STAR " +
                "FROM REVIEWS R JOIN HOTEL H ON R.HOTELID = H.HOTELID " +
                "WHERE USERID = ? AND R.HOTELID = ?";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), userID, hotelID);
    }

    // 유저 리뷰 등록
    public boolean userReviewInsert(ReviewVO vo){
        int result = 0;
        String sql = "INSERT INTO REVIEWS VALUES (REVIEWS_SEQ.NEXTVAL, ?,?,?,?)";
        try {
            result = jdbcTemplate.update(sql, vo.getHotelID(), vo.getUserID(), vo.getContent(), vo.getStar());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    // 유저 리뷰 수정
    public void userReviewUpdate(ReviewVO vo){
        String sql = "UPDATE REVIEWS SET CONTENT = ?, STAR = ? WHERE REVIEWID = ?";
        jdbcTemplate.update(sql, vo.getContent(), vo.getStar(), vo.getReviewID());
    }
  
    public List<ReviewVO> hotelReviewList(int hotelId) {
        // SQL 쿼리 수정
        String sql = "SELECT reviewID, userid, content, star FROM reviews WHERE hotelid = ?";
        return jdbcTemplate.query(sql, new Review1RowMapper(), hotelId);
    }

    // 유저 리뷰 삭제
    public void userReviewDelete(int reviewID){
        String sql = "DELETE REVIEWS WHERE REVIEWID = ?";
        jdbcTemplate.update(sql, reviewID);
    }

    public boolean reviewWrite(ReviewVO reviewVO) {

        int result = 0;
        String sql = "insert into reviews (reviewid , hotelid, userid, content,star) values(reviews_seq.nextval,?,?,?,?) ";
        try {
            result = jdbcTemplate.update(sql, reviewVO.getHotelID(), reviewVO.getUserID(), reviewVO.getContent(), reviewVO.getStar());

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return result > 0;
    }
}
