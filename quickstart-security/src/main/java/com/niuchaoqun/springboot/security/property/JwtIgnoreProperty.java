package com.niuchaoqun.springboot.security.property;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "web.jwt-ignore")
@Data
public class JwtIgnoreProperty {
    private List<String> patterns;

    private List<String> posts;

    private List<String> gets;

    private List<String> deletes;

    private List<String> puts;

    private List<String> heads;
}
