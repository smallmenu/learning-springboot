package com.niuchaoqun.springboot.header;

import com.niuchaoqun.springboot.property.HeaderProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderAuthFilter extends OncePerRequestFilter {

    @Autowired
    private HeaderProperty headerProperty;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(headerProperty.getHeaderKey());
        if (header == null || StringUtils.isBlank(header) || !header.equals(headerProperty.getHeaderValue())) {
            throw new SecurityException();
        }

        filterChain.doFilter(request, response);
    }
}
