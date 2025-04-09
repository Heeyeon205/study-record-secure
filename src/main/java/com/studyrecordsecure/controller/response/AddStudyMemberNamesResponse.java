package com.studyrecordsecure.controller.response;

import com.studyrecordsecure.domain.MemberEntity;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class AddStudyMemberNamesResponse {
    private String loginId;
    private String name;

    public AddStudyMemberNamesResponse(MemberListViewResponse member) {
        this.loginId = member.getLoginId();
        this.name = member.getName();
    }
}
