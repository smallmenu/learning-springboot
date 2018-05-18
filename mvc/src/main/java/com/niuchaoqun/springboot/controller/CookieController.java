package com.niuchaoqun.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class CookieController {
    final private static Logger logger = LoggerFactory.getLogger(CookieController.class);

    /**
     * 通过HttpServletResponse设置cookie
     *
     * @param response
     * @return
     */
    @RequestMapping("/setcookie")
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        Cookie cookie1 = new Cookie("cookie1", "value1");
        cookie1.setMaxAge(1800);
        Cookie cookie2 = new Cookie("cookie2", "value2");
        cookie2.setMaxAge(3600);


        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "cookie set ok";
    }

    /**
     * 通过@CookieValue注解、HttpServletRequest 获取cookie
     *
     * @param cookie1
     * @param request
     * @return
     */
    @RequestMapping("/getcookie")
    @ResponseBody
    public String getCookie(HttpServletRequest request,
            @CookieValue(value = "cookie1", required = false) String cookie1) {

        HashMap<String, String> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }

        logger.info(cookie1);

        return map.toString();
    }

    /**
     * 删除cookie，
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delcookie")
    @ResponseBody
    public String delCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // setValue只是清空了value，cookie还在
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return "delete ok";
    }
}
