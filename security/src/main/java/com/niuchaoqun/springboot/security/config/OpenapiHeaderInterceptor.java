package com.niuchaoqun.springboot.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.security.property.OpenapiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class OpenapiHeaderInterceptor implements HandlerInterceptor {

    @Autowired
    private OpenapiProperty openapiProperty;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(openapiProperty.getHeaderKey());

        if (header == null || !header.equals(openapiProperty.getHeaderValue())) {
            ServletOutputStream os = response.getOutputStream();

            RestResult<Object> error = RestResponse.error(HttpServletResponse.SC_FORBIDDEN, openapiProperty.getError());
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(error);
            os.write(s.getBytes());
            os.flush();
            os.close();
            return false;
        }

        return true;
    }
}
