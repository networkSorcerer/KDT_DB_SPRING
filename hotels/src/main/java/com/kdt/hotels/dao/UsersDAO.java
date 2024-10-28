package com.kdt.hotels.dao;

import com.kdt.hotels.vo.UsersVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Repository
public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<UsersVO> usersSelect(){
        String sql = "SELECT * FROM EMP";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    public boolean UserInsert(UsersVO user) {
        int result = 0;
        String sql = "INSERT INTO USERS (USERID, PASSWORD, NAME, AGE, EMAIL,GRADE) VALUES (?,?,?,?,?,?)";
        try {
            result = jdbcTemplate.update(sql, user.getUserID(), user.getPassword(), user.getName(), user.getAge(), user.getEmail(), user.getGrade());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
    public boolean userDelete(String ID) {
        int result = 0;
        String query = "DELETE FROM USERS WHERE USERID = ?";
        try {
            result = jdbcTemplate.update(query,ID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
    public String userLogin(String ID, String password) { // 로그인 성공시 ID값을 반환받아 로그인상태동안 사용-실험중
        String name = null;
        String query = "SELECT USERID FROM USERS WHERE USERID = ? AND PASSWORD = ?";
        try {
            name = jdbcTemplate.queryForObject(query, String.class, ID, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    public String IDtoName(String ID) {     //유저 ID를 입력 받아 유저 이름 반환
        String name = null;
        String query = "SELECT NAME FROM USERS WHERE USERID = ?";
        try {
            name = jdbcTemplate.queryForObject(query, String.class, ID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }

    private static class UserRowMapper implements RowMapper<UsersVO> {
    @Override
        public UsersVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UsersVO(
                rs.getString("USERID"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getInt("age"),
                rs.getString("EMAIL"),
                rs.getInt("GRADE")
            );
        }
    }
}
