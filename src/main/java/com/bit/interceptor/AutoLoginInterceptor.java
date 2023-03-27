package com.bit.interceptor;

import com.bit.model.MemberDTO;
import com.bit.service.MemberService;
import com.bit.session_name.MemberSessionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AutoLoginInterceptor extends HandlerInterceptorAdapter
        implements MemberSessionName {
    @Autowired
    MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("test interceptor");
        Cookie cookie = WebUtils.getCookie(request, "loginCookie");
        if (cookie != null) {
            MemberDTO memberDTO = memberService.getUserSessionId(cookie.getValue());
            if (memberDTO != null) {
                request.getSession().setAttribute(LOGIN, memberDTO.getMemberId());
            }
        }
        return true;
    }

}





