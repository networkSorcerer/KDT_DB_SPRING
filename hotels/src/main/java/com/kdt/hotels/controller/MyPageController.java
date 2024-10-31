package com.kdt.hotels.controller;

import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MyPageController {
    private final UsersDAO usersDAO;

    public MyPageController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    // 마이페이지 - 페이지
    @GetMapping("/myPage")
    public String userMyPage(Model model, HttpSession session){
        String userID = (String)session.getAttribute("userid");
        UsersVO usersVO = usersDAO.userMyPage(userID);
        model.addAttribute("userVO", usersVO);
        return "thymeleaf/myPage";
    }
    // 마이페이지 - 유저 수정 DB
    @PostMapping("/userUpdate")
    public String userUpdate(Model model, HttpSession session, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("email") String email){
        String userID = (String)session.getAttribute("userid");
        UsersVO vo = new UsersVO();
        vo.setUserID(userID);
        vo.setPassword(password);
        vo.setName(name);
        vo.setAge(age);
        vo.setEmail(email);
        model.addAttribute("userVO", vo);
        usersDAO.userUpdate(vo);
        return "redirect:/";
    }
}
