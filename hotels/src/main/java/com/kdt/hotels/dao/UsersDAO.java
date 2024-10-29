package com.kdt.hotels.dao;

import com.kdt.hotels.mapper.LoginRowMapper;
import com.kdt.hotels.vo.UsersVO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


@Repository
public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<UsersVO> usersSelect(){     //관리자의 모든 유저 확인(이름순)
        String sql = "SELECT * FROM EMP ORDER BY NAME";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    public List<UsersVO> findUserById(String userID) {
        String sql = "SELECT * FROM USERS WHERE USERID = ?";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper(), userID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void userToManagerUpdate(UsersVO user) {  // 유저 등급 수정(관리자 전용)
        String query = "UPDATE EMP SET GRADE = ? WHERE USERID = ?";
        jdbcTemplate.update(query, user.getGrade(), user.getUserID());
    }
    public void userUpdate(UsersVO user) {  // 유저의 개인정보 수정
        String query = "UPDATE EMP SET PASSWORD=?, NAME=?, AGE=?, EMAIL=? WHERE USERID = ?";
        jdbcTemplate.update(query, user.getPassword(),user.getName(),user.getAge(),user.getEmail(), user.getUserID());
    }

    public boolean userExists(String userID) {      // 유저 정보 등록 전 아이디 중복확인
        String sql = "SELECT COUNT(*) FROM USERS WHERE USERID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userID);
        return count != null && count > 0;
    }

    public boolean UserInsert(UsersVO user) {
        if (userExists(user.getUserID())) {     // 아이디 중복 확인 체크
            System.out.println("중복된 아이디입니다.");
            return false;
        }

        int result = 0;
        String sql = "INSERT INTO USERS (USERID, PASSWORD, NAME, AGE, EMAIL,GRADE) VALUES (?,?,?,?,?,0)";   //유저 정보 등록시 그레이드는 자동으로 0 = 체크 불필요
        try {
            result = jdbcTemplate.update(sql, user.getUserID(), user.getPassword(), user.getName(), user.getAge(), user.getEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
    public boolean ManagerInsert(UsersVO user) {
        if (userExists(user.getUserID())) {     // 아이디 중복 확인 체크
            System.out.println("중복된 아이디입니다.");
            return false;
        }
        int result = 0;
        String sql = "INSERT INTO USERS (USERID, PASSWORD, NAME, AGE, EMAIL,GRADE) VALUES (?,?,?,?,?,1)";   //유저 정보 등록시 그레이드는 자동으로 0 = 체크 불필요
        try {
            result = jdbcTemplate.update(sql, user.getUserID(), user.getPassword(), user.getName(), user.getAge(), user.getEmail());
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

    public UsersVO userLogin(String ID, String password) {
        String query = "SELECT USERID, PASSWORD, name, grade FROM USERS WHERE USERID = ? AND PASSWORD = ?"; // 필요한 컬럼 추가
        try {
            return jdbcTemplate.queryForObject(query, new LoginRowMapper(), ID, password);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No matching user found.");
            return null; // 로그인 실패 시 null 반환
        }
    }

    public Integer userGrade(String ID) {
        int grade = 0;
        String query = "SELECT GRADE FROM USERS WHERE USERID = ?";
        try {
            grade = jdbcTemplate.queryForObject(query, Integer.class, ID);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No matching user found.");

        }
        return grade;
    }

    public String IDtoName(String ID) {     //유저 ID를 입력 받아 유저 이름 반환
        String name = null;
        String query = "SELECT NAME FROM USERS WHERE USERID = ?";
        try {
            name = jdbcTemplate.queryForObject(query, String.class, ID);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No matching user found for ID: " + ID);
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
