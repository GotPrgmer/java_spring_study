package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        // 구글 로그인 버튼 클릭하면 구글로그인 창 로그인을 완료 하고 code를 리턴하면 ,(Oauth-Client라이브러리) -> AccessToken 요청
        // 여기까지가 userRequest 정보 이걸 가지고 회원프로필 받아야함 (loadUser 함수를 호출해서 google로부터 회원 프로필을 받음

        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
