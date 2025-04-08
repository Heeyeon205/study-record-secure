package com.studyrecordsecure.domain;

import com.studyrecordsecure.controller.request.UpdateStudyRequest;
import com.studyrecordsecure.controller.response.StudyRecordResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
@Table(name = "study_records")
public class StudyRecordEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id; // study_id BIGINT AUTO_INCREMENT PRIMARY KEY

    private LocalDate studyDay;
    private LocalTime startTime;
    private int studyMins;

    @Lob
    private String contents; // contents TEXT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity member; // member_id BIGINT
    // FOREIGN KEY (member_id) REFERENCES members(member_id)

    public String getEndStudyDay() {
        LocalDateTime endStudyTime = LocalDateTime.of(this.studyDay, this.startTime);
        endStudyTime = endStudyTime.plusMinutes(this.studyMins);
        return endStudyTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }

    @Builder
    public StudyRecordEntity(LocalDate studyDay, LocalTime startTime, int studyMins, String contents, MemberEntity member) {
        this.studyDay = studyDay;
        this.startTime = startTime;
        this.studyMins = studyMins;
        this.contents = contents;
        this.member = member;
    }

    public void updateStudyRecord(UpdateStudyRequest request){
        this.studyDay = request.getStudyDay();
        this.startTime = request.getStartTime();
        this.studyMins = request.getStudyMins();
        this.contents = request.getContents();
    }
}
