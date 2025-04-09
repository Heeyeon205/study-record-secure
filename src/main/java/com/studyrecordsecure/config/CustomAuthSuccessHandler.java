package com.studyrecordsecure.config;

import com.studyrecordsecure.config.auth.CustomUserDetails;
import com.studyrecordsecure.domain.MemberEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails)oAuth2User;
        MemberEntity member = userDetails.getMember();
        response.sendRedirect("/oauth");
    }
}
