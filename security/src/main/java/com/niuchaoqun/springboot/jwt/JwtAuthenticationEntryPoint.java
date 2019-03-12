package com.niuchaoqun.springboot.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuchaoqun.springboot.rest.RestResponse;
import com.niuchaoqun.springboot.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.error("jwt unauthorized {}", e.getMessage());
        ServletOutputStream os = response.getOutputStream();
        RestResult<Object> fail = RestResponse.fail(e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(fail);

        os.write(s.getBytes());
        os.flush();
        os.close();
    }
}
