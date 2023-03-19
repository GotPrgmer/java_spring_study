package com.cos.jwt.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 진입");

        //1. username, password 받아서
        //2. 정상인지 로그인 시도 해보기 authenticationManager로 로그인 시도를 하면 principalDetailsService가 호출
        //3. principalDetailsService가 호출 loadUserByUsername() 함수가 실행됨.
         //4. principalDetailsService를 세션에 담고 (안담으면 권한관리가 안됨.)권한관리를 위해서
        //5. JWT토큰을 만들어서 응답해주면 됨.
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while((input = br.readLine()) != null){ //web에서 요청했을 시,
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper(); // json을 파싱해주는 애
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()); // 토큰 만듦

            //이게 실행되면 PrincipalDetailsServicedml ㅣloadUserByUsername()함수가 실행된다. 토큰에 있는 유저네임만 들어온다 패스워드는 디비가 알아서 해
            //authentication에는 내 로그인한 정보가 담긴다.
            //DB에 있는 ID,PW가 일치
           Authentication authentication =
                    authenticationManager.authenticate(authenticationToken); // 로그인이 정상적으로 되면 authentication이 만들어짐

//            System.out.println(request.getInputStream().toString()); //id랑 pw가 담겨있음.

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 오브젝트를 뱉기때문에 다운캐스팅을 해준다.
            System.out.println(principalDetails.getUser().getUsername());
            //authentication의 username이 뽑힌다는건 로그인이 되었다는 것.
            return authentication; //권한관리를 security가 대신해주기 때문에 편하려고 하는거
            //굳이 JWT토큰을 사용하면서 세션을 만들 필요가 없음. 근데 단지 권한 처리때문에 session 넣어줌
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("================================");
        return null;

//        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
//        ObjectMapper om = new ObjectMapper();
//        LoginRequestDto loginRequestDto = null;
//        try {
//            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);
//
//        // 유저네임패스워드 토큰 생성
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        loginRequestDto.getUsername(),
//                        loginRequestDto.getPassword());
//
//        System.out.println("JwtAuthenticationFilter : 토큰생성완료");
//
//        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
//        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
//        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
//        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
//        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.
//
//        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
//        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
//        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
//        Authentication authentication =
//                authenticationManager.authenticate(authenticationToken);
//
//        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
//        return authentication;
//    }
//



//@Override
//protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                        Authentication authResult) throws IOException, ServletException {
//
//    PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
//
//    String jwtToken = JWT.create()
//            .withSubject(principalDetailis.getUsername())
//            .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//            .withClaim("id", principalDetailis.getUser().getId())
//            .withClaim("username", principalDetailis.getUser().getUsername())
//            .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//
//    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
//}

}
    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT Token 생성해서 request요청한 사용자에게 JWT토큰을 response에 넣어줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증이 완료되었다는 뜻.");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal(); // 오브젝트를 뱉기때문에 다운캐스팅을 해준다.
        String jwtToken = com.auth0.jwt.JWT.create()
            .withSubject("cos토큰")
            .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
            .withClaim("id", principalDetails.getUser().getId())
            .withClaim("username", principalDetails.getUser().getUsername())
            .sign(Algorithm.HMAC512("cos")); // Hash암호방식 RSA X
        response.addHeader("Authorization","Bearer "+jwtToken);
//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
