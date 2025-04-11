package com.studyrecordsecure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;

    @Bean   // BCrypt 단방향 암호화 (해시 함수)
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean    // UsernamePasswordAuthenticationFilter 가 내부에서 해당 클래스로 인증을 위임한다.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // AuthenticationConfiguration: 스프링 시큐리티가 자동 구성한 인증 설정 정보를 담고 있는 객체.
        // AuthenticationManager 는 실제 로그인 시 ID/PW를 검증하고, 인증 여부를 결정하는 핵심 컴포넌트.
        return configuration.getAuthenticationManager();
    }

    @Bean   // SecurityFilterChain 을 거치지 않게 ignore 처리 하는 매서드 (정적 리소스에 활용)
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/static/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // 개발 시 csrf 비활성화

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers( "/", "/home", "/test", "/sign-up", "/login", "/login-form", "/member", "/auth", "/error").permitAll()
                .requestMatchers("/member/**", "/study/**").hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        // 폼 로그인, OAuth2 소셜 로그인 구성 매서드
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(customAuthFailureHandler)
                .defaultSuccessUrl("/test", true)
        ).oauth2Login(oauth2 -> oauth2
                .loginPage("/login-form")
                .successHandler(customAuthSuccessHandler)
                .failureHandler(customAuthFailureHandler)
                .permitAll()
        );

        http.sessionManagement(session -> session
                // 세션 고정 공격 보호. 기존 세션은 유지하고 세션 ID 만 변경 한다.
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
                .maximumSessions(1) // 최대 동시 접속 세션 1개
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단
        );

        return http.build();
    }
}

//csrf 해제 후
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    //     http.csrf(AbstractHttpConfigurer::disable); // 개발할때만
//
//    http
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/home", "/test","/login" , "/" , "/sign-up","/member","/login-form","/auth").permitAll()
//                    .requestMatchers("/member/**").hasAnyRole("STUDENT","ADMIN")
//                    .requestMatchers("/admin").hasRole("ADMIN")
//                    .requestMatchers("/error").permitAll()
//                    .anyRequest().authenticated()
//            );
//
//    http
//            .formLogin(
//                    form->{
//                        form.loginPage("/login-form")
//                                .loginProcessingUrl("/login")// 우리가 만든 로그인페이지로 자동 인터셉트됨
//                                .failureHandler( customAuthFailureHandler)
//                                .defaultSuccessUrl("/test", true);
//
//                    }
//            ).oauth2Login(
//                    oauth2 -> oauth2
//                            .loginPage("/login-form")
//                            .successHandler(customSuccessHandler)
//                            .failureHandler(customAuthFailureHandler)
//                            .permitAll()
//
//            );
//
//    // csrf 해제할때 적용
//    http
//            .logout((auth) -> auth.logoutUrl("/logout")
//                    .logoutSuccessUrl("/"));
//
//    http
//            .sessionManagement((auth) -> auth
//                    .maximumSessions(1)
//                    .maxSessionsPreventsLogin(true));
//
//    http
//            .sessionManagement((auth) -> auth
//                    .sessionFixation().changeSessionId());
//
//
//    return http.build();
//}
