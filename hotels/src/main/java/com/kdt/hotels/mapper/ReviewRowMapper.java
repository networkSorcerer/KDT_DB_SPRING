package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.ReviewVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReviewRowMapper implements RowMapper<ReviewVO> {

    @Override
    public ReviewVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReviewVO(
                rs.getInt("reviewID"),
                rs.getInt("hotelID"),
                rs.getString("hotelName"),
                rs.getString("userID"),
                rs.getString("content"),
                rs.getInt("star")
        );
    }

//    public void reviewResult(List<ReviewVO> list) {
//        System.out.println("---------------------------------------");
//        System.out.println("             리뷰 정보");
//        System.out.println("---------------------------------------");
//
//        for(ReviewVO e : list){
//            System.out.print(e.getEmpNO() + " ");
//            System.out.print(e.getName() + " ");
//            System.out.print(e.getJob() + " ");
//            System.out.print(e.getMgr() + " ");
//            System.out.print(e.getDate() + " ");
//            System.out.print(e.getSal()+" ");
//            System.out.print(e.getComm() + " ");
//            System.out.print(e.getDeptNO());
//            System.out.println();
//        }
//        System.out.println("---------------------------------------");
//    }
}
