package com.cos.jwt.config;


import com.cos.jwt.controller.filter.MyFilter1;
import com.cos.jwt.controller.filter.MyFilter2;
import com.cos.jwt.controller.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

//일반적인 필터는 filterConfig이고 로그인 관련 보안인증은 SecurityConfig
//@Configuration
public class FilterConfig {

//    @Bean
    public FilterRegistrationBean<MyFilter1> filter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*"); // 모든 url에 다 걸어버려라
        bean.setOrder(0);
        return bean;
    }

//    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*"); // 모든 url에 다 걸어버려라
        bean.setOrder(1);
        return bean;
    }

//    @Bean
    public FilterRegistrationBean<MyFilter3> filter3() {
        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
        bean.addUrlPatterns("/*"); // 모든 url에 다 걸어버려라
        bean.setOrder(2);
        return bean;
    }
}
