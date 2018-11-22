package com.niuchaoqun.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {
    private String name;
    private Integer age;
    private String other;
}
