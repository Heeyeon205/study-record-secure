package com.studyrecordsecure.controller.request;

import com.studyrecordsecure.domain.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter // 데이터 조회
@NoArgsConstructor // JSON 역직렬화 시 사용
public class AddMemberRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pw;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    // 도메인 엔티티 변환하기 위해 사용
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .loginId(this.id)
                .password(this.pw)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
