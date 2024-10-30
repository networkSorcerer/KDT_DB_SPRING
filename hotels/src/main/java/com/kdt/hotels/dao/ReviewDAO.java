package com.kdt.hotels.dao;

import com.kdt.hotels.mapper.Review1RowMapper;
import com.kdt.hotels.mapper.ReviewRowMapper;
import com.kdt.hotels.vo.ReviewVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReviewDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저 리뷰 관리
    public List<ReviewVO> userReviewList(String userID){
        String sql = "SELECT R.REVIEWID, R.HOTELID, H.HOTELNAME, R.USERID, R.CONTENT, R.STAR " +
                    "FROM REVIEWS R JOIN HOTEL H ON R.HOTELID = H.HOTELID " +
                    "WHERE USERID = ?";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), userID);
    }

    // 유저 리뷰 수정
    public void userReviewUpdate(ReviewVO vo){
        String sql = "UPDATE REVIEWS SET CONTENT = ?, STAR = ? WHERE REVIEWID = ?";
        jdbcTemplate.update(sql, vo.getContent(), vo.getStar(), vo.getReviewID());
    }
  
    public List<ReviewVO> hotelReviewList(int hotelId) {
        // SQL 쿼리 수정
        String sql = "SELECT userid, content, star FROM reviews WHERE hotelid = ?";
        return jdbcTemplate.query(sql, new Review1RowMapper(), hotelId);
    }

    // 유저 리뷰 삭제
    public void userReviewDelete(int reviewID){
        String sql = "DELETE REVIEWS WHERE REVIEWID = ?";
        jdbcTemplate.update(sql, reviewID);
    }
}
