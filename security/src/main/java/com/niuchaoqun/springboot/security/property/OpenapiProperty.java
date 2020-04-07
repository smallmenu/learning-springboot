package com.niuchaoqun.springboot.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "web.openapi")
@Data
public class OpenapiProperty {
    private String url;

    private String headerKey;

    private String headerValue;

    private String error;
}
