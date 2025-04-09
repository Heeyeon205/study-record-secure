package com.studyrecordsecure.service;

import com.studyrecordsecure.controller.request.AddMemberRequest;
import com.studyrecordsecure.controller.response.MemberListViewResponse;
import com.studyrecordsecure.domain.MemberEntity;
import com.studyrecordsecure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberEntity findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<MemberListViewResponse> findAllMembers() {
        return memberRepository.findByRoleNot(MemberEntity.Role.ROLE_ADMIN)
                .stream()
                .map(MemberListViewResponse::new)
                .toList();
    }

    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    @Transactional
    public void createMember(AddMemberRequest request) {
        if (existsByLoginId(request.getLoginId())) {
            throw new RuntimeException("Member already exists");
        }
        String encodedPassword = passwordEncoder.encode(request.getPw());
        MemberEntity member = MemberEntity.builder()
                .loginId(request.getLoginId())
                .password(encodedPassword)
                .name(request.getName())
                .email(request.getEmail())
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        memberRepository.delete(member);
    }
}
