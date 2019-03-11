package com.niuchaoqun.springboot.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


//public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    public JwtLoginFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            JwtUser jwtUser = new ObjectMapper().readValue(request.getInputStream(), JwtUser.class);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            jwtUser.getUsername(),
//                            jwtUser.getPassword(),
//                            new ArrayList<>()
//                    )
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//        User user = (User) authResult.getPrincipal();
//        String token = new JwtTokenProvider().generateToken(user);
//        response.addHeader("Authorization", token);
//    }
//}
