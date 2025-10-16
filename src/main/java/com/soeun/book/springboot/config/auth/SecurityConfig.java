package com.soeun.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // (선택) 정적 자원/actuator 무시. 여기의 문자열은 DispatcherServlet 매칭이 아니라서 보통 문제를 일으키지 않지만,
        // 혹시 의심되면 이 라인도 잠깐 주석 처리해보고 원인 분리 테스트를 해보세요.
        return web -> web.ignoring().requestMatchers("/actuator/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector introspector) throws Exception {

        // ★ 핵심: MVC 매처 빌더에 servletPath("/")를 명시
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");

        http
                .csrf(AbstractHttpConfigurer::disable)
                // H2 콘솔 프레임 허용 (disable 대신 sameOrigin 권장)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                .authorizeHttpRequests(auth -> auth
                        // H2 콘솔: 서블릿 직접 매핑 → AntPath 사용
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

                        // 나머지 모든 MVC 경로: 문자열 대신 mvc.pattern(...) 사용
                        .requestMatchers(
                                mvc.pattern("/"),
                                mvc.pattern("/css/**"),
                                mvc.pattern("/images/**"),
                                mvc.pattern("/js/**"),
                                mvc.pattern("/profile")
                        ).permitAll()

                        .requestMatchers(mvc.pattern("/api/v1/**")).hasRole("USERS")

                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                );

        return http.build();
    }
}
//@EnableWebSecurity
// · Spring Security 설정을 활성화

//csrf().disable().headers().frameOptions().disable()
// · h2-console 화면 사용하고자 해당 옵션들 disable 함

//authorizeRequests
// · URL 별 권한 관리를 설정하는 옵션의 시작점
// · authorizeRequests가 선언되어야만 antMatchers 옵션을 사용 가능

//antMatchers
// · 권한 관리 대상을 지정하는 옵션
// · URL, HTTP 메소드 별 관리 가능
// · "/" 등 지정된 URL 들은 permitAll() 옵션을 통해 전체 열람 권한을 주었음
// · "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 조회 가능

//anyRequest
// · 설정된 값들 이외 나머지 URL들을 나타냄
// · authenticated를 추가해 나머지 URL들은 모두 인증된 사용자들에게만 허용 = 로그인 사용자에게만 허용

//logout().logoutSuccessUrl("/")
// · 로그아웃 기능에 대한 여러 설정의 진입점, 로그아웃 성공 시 "/" 주소로 이동

//oauth2Login
// · OAuth2 로그인 기능에 대한 여러 설정의 진입점

//userInfoEndpoint
// · OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당

//userService
// · 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
// · 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능