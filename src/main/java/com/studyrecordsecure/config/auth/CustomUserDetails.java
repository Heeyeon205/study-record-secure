package com.studyrecordsecure.config.auth;

import com.studyrecordsecure.domain.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


//    일반 로그인, OAuth2 로그인 모두 지원하기 위해 만든 커스텀 사용자 정보 클래스.
//    인증 이후 SecurityContext 에 저장되는 사용자 정보 객체를 하나로 통일.
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private MemberEntity member;
    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(MemberEntity member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // OAuth2 소셜 로그인
    public CustomUserDetails(MemberEntity member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override   // 사용자의 권한을 전달
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> member.getRole().toString());
        return authorities;
    }

    @Override   // 사용자의 고유 식별자 값을 가져온다.
    public String getName() {
        return member.getProviderId();
    }

    @Override   // 계정 만료 확인 (false: 만료 | true: 계정 유효)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override   // 계정 잠금 확인 (false: 잠김 | true: 계정 유효)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override   // 자격증명 유효 확인 (false: 만료 | true: 유효)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override   // 계정 사용 상태 확인 (false: 비활성 | true: 활성)
    public boolean isEnabled() {
        return true;
    }
}
