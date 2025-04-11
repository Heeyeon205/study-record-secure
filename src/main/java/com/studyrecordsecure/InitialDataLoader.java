package com.studyrecordsecure;

import com.studyrecordsecure.domain.MemberEntity;
import com.studyrecordsecure.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class InitialDataLoader {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //@PostConstruct
    public void loadInitialData() {
        if (memberRepository.findByLoginId("admin").isEmpty()) {
            MemberEntity admin = MemberEntity.builder()
                    .loginId("admin")
                    .password(bCryptPasswordEncoder.encode("admin"))
                    .name("관리자")
                    .email("admin@example.com")
                    .role(MemberEntity.Role.valueOf(String.valueOf(MemberEntity.Role.ROLE_ADMIN)))
                    .build();
            memberRepository.save(admin);
            System.out.println("관리자 계정 생성 완료");
        }

        if (memberRepository.findByLoginId("test1").isEmpty()) {
            MemberEntity student1 = MemberEntity.builder()
                    .loginId("test1")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .name("학생1")
                    .email("student1@example.com")
                    .role(MemberEntity.Role.valueOf(String.valueOf(MemberEntity.Role.ROLE_STUDENT)))
                    .build();
            memberRepository.save(student1);
            System.out.println("학생1 계정 생성 완료");
        }

        if (memberRepository.findByLoginId("test2").isEmpty()) {
            MemberEntity student2 = MemberEntity.builder()
                    .loginId("test2")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .name("학생2")
                    .email("student2@example.com")
                    .role(MemberEntity.Role.valueOf(String.valueOf(MemberEntity.Role.ROLE_STUDENT)))
                    .build();
            memberRepository.save(student2);
            System.out.println("학생2 계정 생성 완료");
        }
    }
}
