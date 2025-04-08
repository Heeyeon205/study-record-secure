package com.studyrecordsecure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        // return "loginForm";
        return "loginForm";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        // return "/member/joinForm2";
        return "/member/joinForm";
    }
}
