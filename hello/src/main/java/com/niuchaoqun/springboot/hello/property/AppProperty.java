package com.niuchaoqun.springboot.hello.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.yml")
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperty {
    private String name;
    private String version;
}
