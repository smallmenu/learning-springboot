package com.niuchaoqun.springboot.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuchaoqun
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperty {
    private String url;

    private String loginUrl;

    private String secret;

    private Long expiration;

    private String header;
}
