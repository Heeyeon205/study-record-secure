package com.studyrecordsecure.repository;

import com.studyrecordsecure.controller.response.MemberListViewResponse;
import com.studyrecordsecure.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByRoleNot(MemberEntity.Role role);

    boolean existsByLoginId(String loginId);

    Optional<MemberEntity> findByLoginId(String loginId);
}
