package com.kdt.hotels.controller;

import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.UsersVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {


    private final UsersDAO usersDAO;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController( UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userid") String userid,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {

        // 단일 UsersVO 객체를 가져옴
        UsersVO user = usersDAO.userLogin(userid, password);
        logger.info("Logged in user: ID = {}, Name = {}, Grade = {}", user.getUserID(), user.getName(), user.getGrade());
        model.addAttribute("user",user);
        if (user != null) {
            session.setAttribute("name", user.getName());
            session.setAttribute("userid", user.getUserID());
            session.setAttribute("grade", user.getGrade());

            if (user.getGrade() == 1) {
                return "redirect:/admin/management"; // redirect로 관리 페이지로 이동
            } else {
                return "Main/LoginMain"; //
            }
        } else {
            logger.warn("Failed login attempt for username: {}", userid);
            model.addAttribute("loginError", "Invalid credentials");
            return "redirect:/"; // 로그인 페이지로 다시 이동
        }
    }

}
