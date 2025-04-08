package com.studyrecordsecure.controller.request;

import com.studyrecordsecure.domain.MemberEntity;
import com.studyrecordsecure.domain.StudyRecordEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class AddStudyRequest {
    private String loginId;
    private LocalDate studyDay;
    private LocalTime startTime;
    private int studyMins;
    private String contents;

    public StudyRecordEntity toEntity(MemberEntity member) {
       return StudyRecordEntity.builder()
               .member(member)
               .studyDay(this.studyDay)
               .startTime(this.startTime)
               .studyMins(this.studyMins)
               .contents(this.contents)
               .build();
    }
}
