package com.kdt.hotels.controller;

import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

    private final UsersDAO usersDAO;

    @Autowired
    public UsersController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UsersVO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(UsersVO user, Model model) {
        boolean isInserted = usersDAO.UserInsert(user);
        model.addAttribute("isInserted", isInserted);
        return "signup";
    }
}

