package com.studyrecordsecure.config.oauth;

public interface OAuth2UserInfo {
    String getProvider();   // 제공자 구분 (Google/Naver)
    String getProviderId(); // 제공자가 발급한 ID
    String getName();   // 사용자 이름
    String getEmail();  // 사용자 이메일
}
