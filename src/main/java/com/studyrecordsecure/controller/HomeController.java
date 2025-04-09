package com.studyrecordsecure.controller;

import com.studyrecordsecure.config.auth.CustomUserDetails;
import com.studyrecordsecure.controller.request.AddMemberRequest;
import com.studyrecordsecure.controller.request.AddStudyRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String signUp(Model model) {
        // return "/member/joinForm2";
        model.addAttribute("member", new AddMemberRequest()); // 반드시 필요
        return "/member/joinForm";
    }

    @GetMapping("/oauth")   // 소셜로그인 사용자 인증 정보 확인 엔드포인트
    public @ResponseBody OAuth2User testLogin(Authentication authentication,
                                              @AuthenticationPrincipal OAuth2User oauth) {
        if (authentication == null) {
            return null;
        }
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        return oauth2User;
    }

    @GetMapping("/error")
    public String handleError(@RequestParam(value = "message", required = false) String message, Model model) {
        if(message != null) {
            model.addAttribute("errorMessage", message);
        }else{
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
        }
        return "error";
    }

    @GetMapping("/test")    // 로그인 사용자가 customUserDetails 객체로 주입되는지 확인
    public @ResponseBody CustomUserDetails test(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return null;
        }
        return customUserDetails;
    }

    @GetMapping("/logout") // 수동 로그아웃 기능, @GetMapping 으로 csrf 활성화 시 사용 불가
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
