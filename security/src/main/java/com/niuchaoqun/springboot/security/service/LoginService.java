package com.niuchaoqun.springboot.security.service;

import com.niuchaoqun.springboot.security.dto.login.LoginForm;
import com.niuchaoqun.springboot.security.jwt.JwtUser;

/**
 * @author niuchaoqun
 */
public interface LoginService {
    String login(LoginForm loginForm);

    JwtUser loginUser();
}
