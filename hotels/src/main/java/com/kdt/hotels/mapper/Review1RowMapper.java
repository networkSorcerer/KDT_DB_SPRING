package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReviewVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Review1RowMapper implements RowMapper<ReviewVO> {
    @Override
    public ReviewVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewVO review = new ReviewVO();
        review.setUserID(rs.getString("userid"));
        review.setContent(rs.getString("content"));
        review.setStar(rs.getInt("star"));
        return review;
    }
}