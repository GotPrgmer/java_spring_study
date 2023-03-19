package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity // spring security filer(SecurityConfig.java) will be registered in spring filter chain
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 , preAuthorize 어노테이션 활성화 그리고 postAuthorize또한 활성화를 시켜주기 때문에
// postAuthorize를 따로 하려고 해도 안됨.
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
                // hasRole('ROLE_USER')")
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
                // hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable(); // csrf disabled
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated() // 이런 주소로 들어오려면 인증이 필요함. 인증만 되면 들어갈 수 있는 주소
//                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // ADMIN 권한이나 MANAGER권한을 가진 사람만 들어오게 함.
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // ADMIN 권한이나 MANAGER권한을 가진 사람만 들어오게 함.
//                .anyRequest().permitAll() // 위 세가지 경로를 제외하고는 전부 권한 허용을 한다. (/일때도 마찬가지이다.) 이렇게 설정하게 되면 login또한 낚아채어지지 않는다.
//                .and()
//                .formLogin()
//                .loginPage("/loginForm")
////                .usernameParameter("username2") // username이 username2으로 form에서 보내진다면 해당 코드가 반드시 있어야한다.
//                .loginProcessingUrl("/login") // /login 주소가 호출이 되면(loginForm으로 /login으로 포스트를 날리면) 시큐리티가 낚아채서 대신 로그인을 진행해줌
//                .defaultSuccessUrl("/") // 로그인이 완료되면 메인페이지 주소로 리다이렉트
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm") // 구글로그인이 완료된 뒤에 후처리가 필요함. 코드를 받고 엑세스토큰을 받고
//                // 시큐리티서버가 사용자정보에 접근가능, 사용자 프로필 정보 가져오고 그정보를 토대로 회원가입을 자동으로 진행
//                // 회원가입시에 필요한 정보가 더 많이 필요하다면, 추가적인 회원가입창이 나와서 회원가입을 해야함
//                // 기본적인 회원가입이 가능해짐.
//                // oauth client는 코드를 받지않고 바로 엑세스토큰이랑 사용자프로필정보를 받아줌
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService); //Oath2UserService 객체여야함 아직 안만들어서 null
//
//
//    }
}
