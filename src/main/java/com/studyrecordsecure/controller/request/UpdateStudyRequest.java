package com.studyrecordsecure.controller.request;

import com.studyrecordsecure.domain.StudyRecordEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateStudyRequest {
    private Long studyId;
    private Long memberId;
    private String memberName;
    private LocalDate studyDay;
    private LocalTime startTime;
    private int studyMins;
    private String contents;

    public StudyRecordEntity toEntity() {
        return StudyRecordEntity.builder()
                .studyDay(this.studyDay)
                .startTime(this.startTime)
                .studyMins(this.studyMins)
                .contents(this.contents)
                .build();
    }
}
