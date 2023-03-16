package com.cos.security1.config.auth;


//시큐리티가 /login 낚아채서 로그인을 진행시킴
// 로그인을 완료되면 시큐리티가 가지고 있는 고유의 session을 만들어줌 (Security ContextHolder라는 곳에 세션정보를 저장함)
// 세션에 들어갈 수 있는 정보는 오브젝트가 정해져있는데 Authentication 타입 객체여야함
// Authentication 안에 User정보가 있어야 됨.
//User 오브젝트의 타입은 UserDetails 타입 객체여야함

// Security Session 영역에  세션 정보를 저장하는데 들어갈 수 잇는 객체는 Authentication객체가 필수적임
// 그 Authentication객체안에 유저정보는 UserDetails타입이어야함
// UserDetails를 꺼내면 유저 오브젝트에 접근이 가능하다.


import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails ,  OAuth2User{

    private User user; //콤포지션
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String,Object> attributes) {

        this.user = user;
        this.attributes = attributes;
    }


    // 해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return user.getRole(); // GrantedAuthority타입인듯
            }
        });
        return collect;
    }




    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리 사이트! 1년동안 회원이 로그인을 안하면! 휴먼 계정으로 하기로 했다면

        // 현재시간 - 로긴시간 => 1년을 초과하면 return false

        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
