package com.kdt.hotels.controller;

import com.kdt.hotels.vo.UsersVO;
import com.kdt.hotels.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String registerUser(@ModelAttribute("user") UsersVO user, Model model) {
        user.setGrade(0); // 기본 등급을 0으로 설정

        if (usersDAO.checkUserIDExists(user.getUserID())) { // 중복 확인
            model.addAttribute("errorMessage", "아이디가 중복됩니다. 다른 아이디를 입력해 주세요.");
            return "Main/signUp"; // 중복 아이디일 경우 다시 회원가입 페이지로 이동
        }

        if (usersDAO.UserInsert(user)) {
            model.addAttribute("successMessage", "회원가입 성공!");
            return "redirect:/"; // 성공 시 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "회원가입 실패");
            return "Main/signUp"; // 실패 시 다시 회원가입 페이지로 이동
        }
    }
}