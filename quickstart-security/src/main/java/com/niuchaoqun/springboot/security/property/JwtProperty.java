package com.niuchaoqun.springboot.security.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "web.jwt")
@Data
public class JwtProperty {
    private String url;

    private String loginUrl;

    private String secret;

    private Long expiration;

    private String header;
}
