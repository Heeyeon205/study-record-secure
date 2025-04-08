package com.studyrecordsecure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // access = AccessLevel.PROTECTED : JPA 만 내 객체를 생성할 수 있게
@ToString(exclude = "studyRecords") // studyRecords 리스트 양방향 관계 시 toString() 호출 순환 참조로 인한 StackOverflow 발생 위험
@Table(name="members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // Member_id BIGINT AUTO_INCREMENT PRIMARY KEY

    @Column(nullable = false, updatable = false, unique = true)
    private String loginId; // username VARCHAR(255) NOT NULL UNIQUE

    @Column(nullable = false)
    private String password; //password VARCHAR(255) NOT NULL

    @Column(nullable = false)
    private String name; // name VARCHAR(255) NOT NULL

    @Column(nullable = false)
    private String email; // email VARCHAR(255) NOT NULL

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;  // role VARCHAR(20)

    @JsonIgnore // JSON 응답 시 이 필드 무시 (무한 순환 참조 방지)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    // cascade = CascadeType.ALL : member 엔티티가 영속/삭제 되면 해당 studyRecords 도 같이 처리
    // orphanRemoval = true : studyRecords 리스트에서 엔티티가 제거 되면 DB 에서 자동 삭제
    private List<StudyRecordEntity> studyRecords;

    // OAuth
    private String provider; // OAuth 식별자
    private String providerId; // 해당 제공자에서 발급한 유저 ID

    @Builder
    public MemberEntity(String loginId, String password, String name, String email, String role,
       String username, String provider, String providerId) {

        if(loginId == null){
            this.loginId = username;
        }else{
            this.loginId = loginId;
        }

        this.password = password;
        this.name = name;
        this.email = email;

        this.role = Role.ROLE_STUDENT;
        if(loginId != null && loginId.equals("admin")){
            this.role = Role.ROLE_ADMIN;
        }else{
            this.role = Role.valueOf(role);
        }

        if(provider != null){
            this.provider = provider;
        }
        if(providerId != null){
            this.providerId = providerId;
        }
    }

    public enum Role {
        ROLE_ADMIN, ROLE_STUDENT
    }
}
