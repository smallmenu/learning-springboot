package com.niuchaoqun.springboot.security.controller;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@Slf4j
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public RestResult<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateTokenByString(authenticate);

        return RestResponse.data(token);
    }

    @RequestMapping("test1")
    @ResponseBody
    public String test1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getDetails();
        return "jwt test1";
    }

    @RequestMapping("test2")
    @ResponseBody
    public String test2() {
        return "jwt test1";
    }
}
