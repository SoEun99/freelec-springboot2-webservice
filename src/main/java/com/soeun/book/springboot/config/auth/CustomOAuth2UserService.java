package com.soeun.book.springboot.config.auth;

import com.soeun.book.springboot.config.auth.dto.OAuthAttributes;
import com.soeun.book.springboot.config.auth.dto.SessionUser;
import com.soeun.book.springboot.domain.user.User;
import com.soeun.book.springboot.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());
        return userRepository.save(user);
    }


}

//registrationId
// · 현재 로그인 진행 중인 서비스를 구분하는 코드
// · 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시 플랫폼 구분을 위해 사용

//userNameAttributeName
// · OAuth2 로그인 시 '키가 되는 필드값'을 뜻함 = primary key
// · 구글의 경우 기본적으로 코드 지원(sub), 네이버, 카카오 등은 기본 지원하진 않음

//OAuthAttributes
// · OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
// · 이후 다른 소셜 로그인도 이 클래스를 사용함

//SessionUser
// · 세션에 사용자 정보를 저장하기 위한 Dto 클래스
// · 왜 User 클래스를 쓰지 않고 새로 생성해 쓰는지는 SessionUser에서 확인 가능

//Update 기능
// · 구글 사용자 정보가 변경되었거나 업데이트 되었을 경우를 대비, update 기능도 같이 구현 됨
// · 사용자 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영됨