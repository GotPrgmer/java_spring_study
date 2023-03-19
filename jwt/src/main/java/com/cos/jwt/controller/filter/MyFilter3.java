package com.cos.jwt.controller.filter;

import lombok.Builder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{



    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;


        if(req.getMethod().equals("POST")){
        System.out.println("POST요청됨");
        String headerAuth = req.getHeader("Authorization");
        System.out.println(headerAuth);

        if(headerAuth.equals("cos")){
            chain.doFilter(req, res); // 코스가 들어있으면 체인을 타게한다.
            System.out.println("인증됨");
        }else{
            PrintWriter out = res.getWriter();
            out.println("인증안됨");

        }
        }
    }

}
