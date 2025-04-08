package com.studyrecordsecure.service;

import com.studyrecordsecure.controller.request.AddStudyRequest;
import com.studyrecordsecure.controller.request.UpdateStudyRequest;
import com.studyrecordsecure.controller.response.StudyListViewResponse;
import com.studyrecordsecure.controller.response.StudyRecordResponse;
import com.studyrecordsecure.domain.MemberEntity;
import com.studyrecordsecure.domain.StudyRecordEntity;
import com.studyrecordsecure.repository.MemberRepository;
import com.studyrecordsecure.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyRecordService {
    private final StudyRecordRepository studyRecordRepository;
    private final MemberRepository memberRepository;

    public StudyRecordEntity findByStudyId(Long id) {
        return studyRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("StudyRecord not found"));
    }

    public List<StudyListViewResponse> findAllStudyRecords() {
        return studyRecordRepository.findAll()
                .stream()
                .map(StudyListViewResponse::new)
                .toList();
    }

    @Transactional
    public void createStudyRecord(AddStudyRequest request) {
        MemberEntity member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalStateException("Member not found"));
        StudyRecordEntity studyRecord = request.toEntity(member);
        studyRecordRepository.save(studyRecord);
    }

    @Transactional
    public void update(UpdateStudyRequest request){
        StudyRecordEntity studyRecord = studyRecordRepository.findById(request.getStudyId())
                .orElseThrow(() -> new IllegalArgumentException("Study record not found"));
        studyRecord.updateStudyRecord(request);
    }

    @Transactional
    public void delete(Long id) {
        StudyRecordEntity studyRecord = findByStudyId(id);
        studyRecordRepository.delete(studyRecord);
    }
}
