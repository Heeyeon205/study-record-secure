package com.studyrecordsecure.config.oauth;

import com.studyrecordsecure.config.auth.CustomUserDetails;
import com.studyrecordsecure.config.oauth.provider.GoogleUserInfo;
import com.studyrecordsecure.config.oauth.provider.NaverUserInfo;
import com.studyrecordsecure.domain.MemberEntity;
import com.studyrecordsecure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

// 소셜 로그인 사용자 인증 처리 서비스 (로그인 / 회원가입)처리 후 사용자 객체 반환
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override   // 소셜로그인 후 해당 소셜로 부터 사용자 데이터를 받아온다.
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        return processOAuthUser(request, oAuth2User);
    }

    private OAuth2User processOAuthUser(OAuth2UserRequest request, OAuth2User oAuth2User) {
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = switch (registrationId) {
            case "google" -> new GoogleUserInfo(oAuth2User.getAttributes());
            case "naver" -> new NaverUserInfo(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인 형식입니다.");
        };

        MemberEntity member = memberRepository
                .findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())
                .orElseGet(() -> {  // 해당 사용자가 있으면 DB 에서 반환, 없으면 회원가입 처리
                    MemberEntity joinMember = MemberEntity.builder()
                            .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                            .name(oAuth2UserInfo.getName())
                            .email(oAuth2UserInfo.getEmail())
                            .password(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()))   // 사용하지 않는 비밀번호
                            .provider(oAuth2UserInfo.getProvider())
                            .providerId(oAuth2UserInfo.getProviderId())
                            .build();
                    return memberRepository.save(joinMember);
                });
        // CustomUserDetails 에 인증 정보를 넘겨주고 CustomUserDetails 는 SecurityContext 에 인증 정보 저장
        return new CustomUserDetails(member, oAuth2User.getAttributes());
    }
}
