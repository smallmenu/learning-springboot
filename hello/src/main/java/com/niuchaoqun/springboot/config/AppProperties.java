package com.niuchaoqun.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties")
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AppConfig{" + "name='" + name + '\'' + ", version='" + version + '\'' + '}';
    }
}
