package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

        // code를 통해 구성한 정보
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청~~");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

//        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
//            System.out.println("페이스북 로그인 요청~~");
//            oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
//            System.out.println("네이버 로그인 요청~~");
//            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("우리는 구글과 페이스북만 지원해요");
        }

        //System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
        //System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
        Optional<User> userOptional =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // user가 존재하면 update 해주기
            user.setEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        } else {
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
//        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
//        // 구글 로그인 버튼 클릭하면 구글로그인 창 로그인을 완료 하고 code를 리턴하면 ,(Oauth-Client라이브러리) -> AccessToken 요청
//        // 여기까지가 userRequest 정보 이걸 가지고 회원프로필 받아야함 (loadUser 함수를 호출해서 google로부터 회원 프로필을 받음
//
//
//        OAuth2User oauth2User = super.loadUser(userRequest);
//
//        System.out.println("getAttributes: " + oauth2User.getAttributes());
//
//        String provider = userRequest.getClientRegistration().getClientId(); // google
//        String providerId = oauth2User.getAttribute("sub"); // providerId Sub
//        String username = provider + "_" + providerId; // google_providerId
//        String password = bCryptPasswordEncoder.encode("겟인데어");
//        String email = oauth2User.getAttribute("email");
//        String role = "ROLE_USER";
//
//        User userEntity = userRepository.findByUsername(username);
//
//        if (userEntity == null){
//            System.out.println("최초 구글로그인");
//            userEntity = User.builder()
//                    .username(username)
//                    .password(password)
//                    .email(email)
//                    .role(role)
//                    .provider(provider)
//                    .providerId(providerId)
//                    .build();
//            userRepository.save(userEntity);
//        }else
//        {
//            System.out.println("구글로그인 이미 로그인이 되어있습니다. 자동회원가입되어있음.");
//
//        }
//
//
//
//
//
//        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
//    }
}
