package com.niuchaoqun.springboot.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHander implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("jwt access error: {}", e.getMessage());

        ServletOutputStream os = response.getOutputStream();

        RestResult<Object> error = RestResponse.error(HttpServletResponse.SC_FORBIDDEN, e.getLocalizedMessage());
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(error);
        os.write(s.getBytes());
        os.flush();
        os.close();
    }
}
