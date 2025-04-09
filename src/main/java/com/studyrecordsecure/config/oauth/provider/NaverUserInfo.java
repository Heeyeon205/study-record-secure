package com.studyrecordsecure.config.oauth.provider;

import com.studyrecordsecure.config.oauth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> NaverUserAttributes;

    @Override
    public String getProvider() {
        return "naver";
    }

    private Map<String, Object> getResponse() {
        return (Map<String, Object>) NaverUserAttributes.get("response");
    }

    @Override
    public String getProviderId() {
//        return NaverUserAttributes.get("id").toString();
//        return (String) NaverUserAttributes.get("id");
        return String.valueOf(getResponse().get("id"));
    }

    @Override
    public String getName() {
//        return NaverUserAttributes.get("name").toString();
//        return (String) NaverUserAttributes.get("name");
        return String.valueOf(getResponse().get("name"));
    }


    @Override
    public String getEmail() {
//        return NaverUserAttributes.get("email").toString();
//        return (String) NaverUserAttributes.get("email");
        return String.valueOf(getResponse().get("email"));
    }
}
