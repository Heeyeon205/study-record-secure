package com.studyrecordsecure.repository;

import com.studyrecordsecure.domain.StudyRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRecordRepository extends JpaRepository<StudyRecordEntity, Long> {
}
