package com.kdt.hotels.dao;

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
    public List<ReviewVO> userReviewList(){
        String sql = "SELECT R.REVIEWID, R.HOTELID, H.HOTELNAME, R.USERID, R.CONTENT, R.STAR " +
                    "FROM REVIEWS R JOIN HOTEL H ON R.HOTELID = H.HOTELID";
        return jdbcTemplate.query(sql, new ReviewRowMapper());
    }

    // 유저 리뷰 수정
    public void userReviewUpdate(ReviewVO vo){
        String sql = "UPDATE REVIEW SET CONTENT, START FROM REVIEWS WHERE REVIEWID = ?";
        jdbcTemplate.update(sql, vo.getReviewID());
    }
}
