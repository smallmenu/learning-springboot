package com.niuchaoqun.springboot.security.config;

import com.niuchaoqun.springboot.security.property.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProperty jwtProperty;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(jwtProperty.getUrl())
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
