package com.niuchaoqun.springboot.security.config;

import com.niuchaoqun.springboot.security.property.OpenapiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class OpenapiInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private OpenapiProperty openapiProperty;

    @Autowired
    private OpenapiHeaderInterceptor exportHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exportHeaderInterceptor).addPathPatterns(openapiProperty.getUrl());
    }
}
