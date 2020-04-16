package com.niuchaoqun.springboot.security.service.impl;

import com.niuchaoqun.springboot.security.dto.login.LoginForm;
import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.entity.AdminLoginLog;
import com.niuchaoqun.springboot.security.jwt.JwtTokenProvider;
import com.niuchaoqun.springboot.security.jwt.JwtUser;
import com.niuchaoqun.springboot.security.mapper.AdminLoginLogMapper;
import com.niuchaoqun.springboot.security.mapper.AdminMapper;
import com.niuchaoqun.springboot.security.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminLoginLogMapper adminLoginLogMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String login(LoginForm loginForm) {
        LocalDateTime now = LocalDateTime.now();
        String clientIp = clientIp();
        AdminLoginLog adminLoginLog = AdminLoginLog.builder()
                .logined(now)
                .loginIp(clientIp)
                .build();

        Authentication authenticate;
        try {
            // 验证登录
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword())
            );
            // 写入上下文
            JwtUser jwtUser = (JwtUser) authenticate.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtTokenProvider.generateTokenByString(authenticate);

            // 最近登录
            Admin admin = adminMapper.selectByPrimaryKey(jwtUser.getId());
            admin.setLastLogined(now);
            admin.setLastLoginIp(clientIp);
            adminMapper.updateByPrimaryKeySelective(admin);

            // 登录成功日志
            adminLoginLog.setAdminId(admin.getId());
            adminLoginLog.setUsername(admin.getUsername());
            adminLoginLog.setState(1);
            adminLoginLogMapper.insertSelective(adminLoginLog);

            return token;
        } catch (LockedException e) {
            throw new LockedException("账号已锁定");
        } catch (DisabledException e) {
            throw new DisabledException("账号已被禁用");
        } catch (BadCredentialsException e) {
            // 登录失败日志
            Admin admin = adminMapper.selectOne(Admin.builder().username(loginForm.getUsername()).build());
            if (admin != null) {
                adminLoginLog.setAdminId(admin.getId());
                adminLoginLog.setUsername(admin.getUsername());
                adminLoginLogMapper.insertSelective(adminLoginLog);
            }

            log.info(e.getLocalizedMessage());
            throw new BadCredentialsException("账号或密码错误");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public JwtUser logined() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 未登录状态下返回 Null
        if (authentication != null && authentication.getPrincipal().getClass().equals(JwtUser.class)) {
            return (JwtUser) authentication.getPrincipal();
        }

        return null;
    }

    private String clientIp() {
        String remoteAddr = request.getRemoteAddr();
        String realIp = request.getHeader("X-Real-IP");
        String clientIp = realIp != null ? realIp : remoteAddr;

        return "0:0:0:0:0:0:0:1".equals(clientIp) ? "127.0.0.1" : clientIp;
    }
}
