package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver; // 반드시 서블렛이 들고 있는 머스터치를 임포트 한다.
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setCharset("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html"); //This file will be read as mustache as well as html file


        registry.viewResolver(resolver);
    }
}
