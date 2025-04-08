package com.studyrecordsecure.controller.response;

import com.studyrecordsecure.domain.StudyRecordEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class StudyRecordResponse {
    private final Long studyid;
    private final Long memberId;
    private final String memberName;
    private final LocalDate studyDay;
    private final LocalTime startTime;
    private final int studyMins;
    private final String contents;

    public StudyRecordResponse(StudyRecordEntity study){
        this.studyid = study.getId();
        this.memberId = study.getMember().getId();
        this.memberName = study.getMember().getName();
        this.studyDay = study.getStudyDay();
        this.startTime = study.getStartTime();
        this.studyMins = study.getStudyMins();
        this.contents = study.getContents();
    }
}
