package com.cos.jwt.config.auth;


import com.cos.jwt.controller.repository.UserRepository;
import com.cos.jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//http://localhost:8080/login할때 동작을 한다. 스프링시큐리티 로그인 기본 요청 주소가 login이기때문.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername");
        User userEntity = userRepository.findByUsername(username);
        System.out.println("userEntity" + userEntity);
        return new PrincipalDetails(userEntity);
    }
}
