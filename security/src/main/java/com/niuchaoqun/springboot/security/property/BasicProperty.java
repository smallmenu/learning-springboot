package com.niuchaoqun.springboot.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "web.basic")
@Data
public class BasicProperty {
    private String url;

    private String user;

    private String password;
}
