package com.studyrecordsecure.controller;

import com.studyrecordsecure.controller.request.AddStudyRequest;
import com.studyrecordsecure.controller.request.UpdateStudyRequest;
import com.studyrecordsecure.controller.response.AddStudyMemberNamesResponse;
import com.studyrecordsecure.controller.response.StudyListViewResponse;
import com.studyrecordsecure.controller.response.StudyRecordResponse;
import com.studyrecordsecure.domain.StudyRecordEntity;
import com.studyrecordsecure.service.MemberService;
import com.studyrecordsecure.service.StudyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyRecordService studyRecordService;
    private final MemberService memberService;

    @GetMapping("/records")
    public String listStudyRecords(Model model) {
        List<StudyListViewResponse> list = studyRecordService.findAllStudyRecords();
        if(list.isEmpty()) {
            return "redirect:/home";
        }
        model.addAttribute("list", list);
        return "study/list";
    }

    @GetMapping({"", "/"})
    public String study(Model model) {
        List<AddStudyMemberNamesResponse> members = memberService.findAllMembers()
                .stream()
                .map(AddStudyMemberNamesResponse::new)
                .toList();
        model.addAttribute("members", members);
        return "study/addForm";
    }

    @PostMapping({"", "/"})
    public String createStudyRecord(@ModelAttribute AddStudyRequest request, Model model) {
        if (request.getStudyDay().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("오늘 이후 날짜는 기록할 수 없습니다.");
        }
        try {
            studyRecordService.createStudyRecord(request);
            return "redirect:/study/records";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "study/addForm";
        }
    }

    @GetMapping("/{id:[0-9]+}")
    public String updateForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("curdate", LocalDate.now());
            model.addAttribute("runtime", LocalDateTime.now());
            StudyRecordEntity studyRecord = studyRecordService.findByStudyId(id);
            model.addAttribute("members", new StudyRecordResponse(studyRecord));
            return "study/updateForm";
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:study/records";
        }
    }

    @PostMapping("/update")
    public String updateStudyRecord(@ModelAttribute UpdateStudyRequest request, Model model) {
        try {
            studyRecordService.update(request);
            return "redirect:/study/records";
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:study/records";
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudyRecord(@PathVariable Long id) {
        try {
            studyRecordService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
