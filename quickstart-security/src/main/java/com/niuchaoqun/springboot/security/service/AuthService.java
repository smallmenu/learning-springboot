package com.niuchaoqun.springboot.security.service;

import com.niuchaoqun.springboot.security.dto.login.LoginForm;
import com.niuchaoqun.springboot.security.jwt.JwtUser;


public interface AuthService {
    /**
     * 登录接口
     *
     * @param loginForm
     * @return
     */
    String login(LoginForm loginForm);

    /**
     * 获取当前已登录用户
     *
     * @return
     */
    JwtUser logined();
}
