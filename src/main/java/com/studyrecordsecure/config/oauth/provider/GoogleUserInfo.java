package com.studyrecordsecure.config.oauth.provider;

import com.studyrecordsecure.config.oauth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> googleUserAttributes;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return googleUserAttributes.get("sub").toString();
    }

    @Override
    public String getName() {
        return googleUserAttributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return googleUserAttributes.get("email").toString();
    }
}
