package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.jwt.JwtTokenProvider;
import com.niuchaoqun.springboot.rest.RestResponse;
import com.niuchaoqun.springboot.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public RestResult<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.getStringFromToken(authenticate);

        return RestResponse.data(token);
    }

}
