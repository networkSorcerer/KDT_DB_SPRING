package com.kdt.hotels.controller;

import com.kdt.hotels.vo.UsersVO;
import com.kdt.hotels.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private UsersDAO usersDAO;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new UsersVO());
        return "Main/signUp";
    }

    @PostMapping("/signup")
    public String registerUser(UsersVO user, Model model) {
        user.setGrade(0); // 등급을 0으로 설정
        if (usersDAO.UserInsert(user)) {
            model.addAttribute("successMessage", "회원가입 성공!");
        } else {
            model.addAttribute("errorMessage", "회원가입 실패: 중복된 아이디입니다.");
        }
        return "Main/signUp";
    }
}