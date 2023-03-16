package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설저에서 loginnProcessingUrl("/login")로 인해서
// /login 요청이 들어오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 LoadUserByUsername 함수가 실행이 된다.

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session에 Authentication타입 그리고 그 안에 UserDetails타입
    // 시큐리티 session(Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null){
            return new PrincipalDetails(userEntity); // user오브젝트를 넣어줘야 활용하기 편함
            // Authentication(내부 UserDetails)
        }
        return null;
    }
}
