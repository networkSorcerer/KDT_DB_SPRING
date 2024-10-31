package com.kdt.hotels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(){
        return "main/Main";
    }
    @PostMapping("/")
    public String homeMove(){
        return "redirect:/";
    }
}
