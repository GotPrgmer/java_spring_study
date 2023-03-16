package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // returning view
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
        @AuthenticationPrincipal PrincipalDetails userDetails){ // 현재 사용자 정보를 가지고 있는 UserDetails을 상속받은 PrincipalDetails  DI주입
        System.out.println("/test/login ===================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication: " + principalDetails.getUser());

        System.out.println("userDetails : " + userDetails.getUser());

        return "세션 정보 확인하기";

    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oauth){ // 현재 사용자 정보를 가지고 있는 UserDetails을 상속받은 PrincipalDetails  DI주입
        System.out.println("/test/login ===================");
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal(); // Oauth2User객체로 다운캐스팅
        System.out.println("authentication: " + oauth2User.getAttributes());
        System.out.println("oauth2User: " + oauth.getAttributes());


        return "OAuth 세션 정보 확인하기";

    }


    //localhost:8080/
    ///localhost:8080
    @GetMapping({"","/"})
    public String index(){
        //mustache , default folder location : src/main/resources/
        //settings for view resolver : templates (prefix) .mustache (suffix) could be omitted!
        return "index"; // This code will call the file located in src/main/resources/templates/index.mustache
    }
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails" + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // 스프링 시큐리티 해당주소를 낚아챔
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){

        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        System.out.println(user);

        return "redirect:/loginForm";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // data함수가 호출되기 직전에 실행
//    @PostAuthorize() // 하려면
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }

}
