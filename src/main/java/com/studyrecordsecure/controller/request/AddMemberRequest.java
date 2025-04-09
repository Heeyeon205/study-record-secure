package com.studyrecordsecure.controller.request;

import com.studyrecordsecure.domain.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter // 데이터 조회
@Setter // 데이터 삽입
@NoArgsConstructor // JSON 역직렬화 시 사용
public class AddMemberRequest {
    private String loginId;
    private String pw;
    private String email;
    private String name;


    // 도메인 엔티티 변환하기 위해 사용
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .loginId(this.loginId)
                .password(this.pw)
                .email(this.email)
                .name(this.name)
                .build();
    }
}
