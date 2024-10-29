package com.kdt.hotels.mapper;

import com.kdt.hotels.vo.UsersVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRowMapper implements RowMapper<UsersVO> {
    @Override
    public UsersVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsersVO user = new UsersVO();
        user.setUserID(rs.getString("USERID"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setName(rs.getString("name"));
        user.setGrade(rs.getInt("grade"));
        return user;
    }
}
