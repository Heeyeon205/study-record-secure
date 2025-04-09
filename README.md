# 🔐 study-record-secure

### 개요
- 공부 시간을 기록하고 관리하는 간단한 웹 애플리케이션.  
- 회원가입 및 로그인은 **도메인 방식과 소셜 방식(Google, Naver)**.  
- **Spring Security 기반 인증/인가 구현**이 핵심.


### ERD Diagram
<img width="566" alt="study-record ERD" src="https://github.com/user-attachments/assets/9bfb650a-8a43-4fc5-9879-8a933b169e68" />

<br>

### 요구사항
|서비스|필요기능 설명|부가 기능|
|--|--|--|
|관리자|회원 삭제, 공부 기록 삭제||
|회원가입|도메인 회원가입, 소셜 회원가입|소셜 회원가입/로그인 자동 처리|
|로그인|도메인 로그인, 소셜 로그인|로그인 fail 시 비동기 에러 메세지|
|회원목록|admin 제외 전체 회원 출력, 본인 정보 수정, 삭제||
|공부 기록 등록|학생 이름, 시작일, 시작시간, 학습 시간, 학습 내용|오늘 이후의 시간은 등록할 수 없음|
|공부 기록 목록|전체 학생들의 공부 기록 출력, 본인 기록 수정, 삭제||

<br>

### 서버 설계 순서
|순서|내용|클래스|
|--|--|--|
|1|프로젝트 환경 설정| `build.grale`, `application.yml`, `Database Connection`|
|2|도메인 정의|`MemberEntity`, `StudyRecordEntity`|
|3|JPA 설정|`MemberRepository`, `StudyRecordRepository`|
|4|서비스 계층 + DTO 작성|`MemberService`, `StudyRecordRepository`, `Response/**`, `Request/**`|
|5|컨트롤러 계층 작성|`MemberController`, `StudyRecordController`|
|6|라우팅 컨트롤러 작성|`HomeController`, `AdminController`|
|7|시큐리티 기본 설정|`SecurityConfig`|
|8|도메인 로그인 시큐리티 구현|`CustomUserDetails`, `CustomUserDetailsService`|
|9|소셜 로그인 시큐리티 구현|`OAuth2UserInfo`, `GoogleUserInfo`, `NaverUserInfo`, `OAuth2UserService`|
|10|시큐리티 경로 설정|`SecurityConfig`|
|11|로그인 결과 핸들러 구현|`CustomAuthSuccessHandler`, `CustomAuthFailureHandler`|
|12|라우팅 컨트롤러 에러페이지, 로그인 결과 추가|`HomeController`|

<br> 

### 설계 포인트
- Spring Security 구조: 커스텀 UserDetailsService + OAuth2 사용자 매핑
- 도메인-소셜 통합 로그인 처리 구조: OAuth2UserInfo 추상화 → Provider별 전략 패턴
- Exception 처리 흐름: 로그인 실패 핸들러에서 비동기 응답 제공
- 권한/역할 설계: ROLE_ADMIN, ROLE_USER 기반 경로 제한

