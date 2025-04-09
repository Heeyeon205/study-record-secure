package com.studyrecordsecure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdimController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin Controller";
    }
}
