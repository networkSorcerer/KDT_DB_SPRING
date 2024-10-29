package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReviewVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<ReviewVO> {

    @Override
    public ReviewVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReviewVO(
                rs.getInt("REVIEWID"),
                rs.getInt("HOTELID"),
                rs.getString("HOTELNAME"),
                rs.getString("USERID"),
                rs.getString("CONTENT"),
                rs.getInt("STAR")
        );
    }
}
