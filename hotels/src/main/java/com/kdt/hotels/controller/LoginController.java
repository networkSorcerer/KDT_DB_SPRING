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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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



        boolean existUser = usersDAO.checkUserIDExists(userid);

        if (!existUser) {
            logger.warn("Failed login attempt: user ID does not exist: {}", userid);
            model.addAttribute("loginError", "ID가 존재하지 않습니다.");
            return "/Main/Main"; // 로그인 페이지로 다시 이동
        }
        UsersVO user = usersDAO.userLogin(userid, password);
        model.addAttribute("user",user);
        if (user != null && existUser == true) {
            logger.info("Logged in user: ID = {}, Name = {}, Grade = {}", user.getUserID(), user.getName(), user.getGrade());
            session.setAttribute("name", user.getName());
            session.setAttribute("userid", user.getUserID());
            session.setAttribute("grade", user.getGrade());

            if (user.getGrade() == 1) {
                return "redirect:/admin/management"; // redirect로 관리 페이지로 이동
            } else if (user.getGrade() == 3) {
                return "Main/mainMenu";
            }else {
                return "/Main/Main"; //
            }
        } else {
            logger.warn("Failed login attempt for username: {}", userid);
            model.addAttribute("loginError", "잘못된 비밀번호 입니다.");
            return "/Main/Main"; // 로그인 페이지로 다시 이동
        }
    }
    @PostMapping("/login2")
    public String login2(
            @RequestParam("userid") String userid,
            @RequestParam("password") String password,
            @RequestParam("hotelId") int hotelId,
            @RequestParam("hotelName") String hotelName,
            Model model,
            HttpSession session, RedirectAttributes redirectAttributes) {

        System.out.println(hotelId);
        boolean existUser = usersDAO.checkUserIDExists(userid);
        redirectAttributes.addAttribute("hotelId", hotelId); // 호텔 ID 추가
        redirectAttributes.addAttribute("userid", userid); // 호텔 ID 추가
        redirectAttributes.addAttribute("hotelName", hotelName);
        if (!existUser) {
            logger.warn("Failed login attempt: user ID does not exist: {}", userid);
            model.addAttribute("loginError", "ID가 존재하지 않습니다.");
            return "redirect:/hotel/selectRoom";  // 로그인 페이지로 다시 이동
        }
        UsersVO user = usersDAO.userLogin(userid, password);
        model.addAttribute("user",user);
        if (user != null && existUser == true) {
            logger.info("Logged in user: ID = {}, Name = {}, Grade = {}", user.getUserID(), user.getName(), user.getGrade());
            session.setAttribute("name", user.getName());
            session.setAttribute("userid", user.getUserID());
            session.setAttribute("grade", user.getGrade());

            if (user.getGrade() == 1) {
                return "redirect:/admin/management"; // redirect로 관리 페이지로 이동
            } else if (user.getGrade() == 3) {
                return "Main/mainMenu";
            }else {
                return "redirect:/hotel/selectRoom";  //
            }
        } else {
            logger.warn("Failed login attempt for username: {}", userid);
            model.addAttribute("loginError", "잘못된 비밀번호 입니다.");
            return "redirect:/hotel/selectRoom";  // 로그인 페이지로 다시 이동
        }
    }


}
