package com.niuchaoqun.springboot.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "header")
@Data
public class HeaderProperty {
    private String url;

    private String headerKey;

    private String headerValue;
}
