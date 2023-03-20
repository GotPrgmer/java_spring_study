package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.controller.repository.UserRepository;
import com.cos.jwt.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//시큐리티가 filter가지고 있는데 필터 중에서 BasicAuthentciationFilter라는 것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음.
// 인증이 필요없어도 필터를 무조건 탐.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain); //응답을 한번 하고
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader : " + jwtHeader); //jwt헤더를 넘길것임.

        //JWT토큰을 검사해서 정상적인 사용자인지 확인
        if (jwtHeader == null | !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request,response);
            return;
        }
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("username").asString();
        //서명이 잘 됨
        if(username != null){
            System.out.println("username 정상");
            User userEntity = userRepository.findByUsername(username);
            System.out.println("userEntity : " + userEntity.getUsername());
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            System.out.println("principalDetails : " + principalDetails.getUsername() +"ㄹ낭ㄻㄴㅇㄹ");
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            //서명이 잘 됏으니까 authentication을 만들어 준것이다. 권한도 마지막에 넣어주자.
            //서명이 정상이면 Authentication객체를 만들어준다.
            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response); // 로그인이 잘 됐으니까 그 후로 체인을 타게 한다.

        }


    }
}