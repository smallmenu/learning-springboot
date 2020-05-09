package com.niuchaoqun.springboot.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


@Configuration
@ConfigurationProperties(prefix = "web.config")
@Data
public class ConfigProperty {
    private Set<String> tableCache;
}
