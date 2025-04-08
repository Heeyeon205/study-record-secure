package com.studyrecordsecure.controller.response;

import com.studyrecordsecure.domain.StudyRecordEntity;
import lombok.Getter;

@Getter
public class StudyListViewResponse {
    private final Long studyId;
    private final String memberName;
    private final String studyDay;
    private final String startTime;
    private final int studyMins;
    private final String endStudyDay;
    private final String contents;

    public StudyListViewResponse(StudyRecordEntity study) {
        this.studyId = study.getId();
        this.memberName = study.getMember().getName();
        this.studyDay = study.getStudyDay().toString();
        this.startTime = study.getStartTime().toString();
        this.studyMins = study.getStudyMins();
        this.endStudyDay = study.getEndStudyDay().toString();
        this.contents = study.getContents().toString();
    }
}
