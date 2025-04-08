package com.studyrecordsecure.controller;

import com.studyrecordsecure.controller.request.AddMemberRequest;
import com.studyrecordsecure.controller.response.MemberListViewResponse;
import com.studyrecordsecure.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public String members(Model model) {
        List<MemberListViewResponse> list = memberService.findAllMembers();
        if (list.isEmpty()) {
            return "member/joinForm";
        }
        model.addAttribute("list", list);
        return "member/list";
    }

    @PostMapping({"", "/"})
    public String join(@Valid @ModelAttribute AddMemberRequest request, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/joinForm";
        }
        try {
            memberService.createMember(request);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "member/joinForm";
        }
    }

    @GetMapping("/del/{id}")
    public String delete(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return "redirect:/member/members";
        } catch (Exception e) {
            e.printStackTrace();
            // 에러페이지로 전송
        }
        return "redirect:/member/members";
    }

    @ResponseBody
    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity deleteMember(@PathVariable Long id) {
        try{
            memberService.deleteMember(id);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
