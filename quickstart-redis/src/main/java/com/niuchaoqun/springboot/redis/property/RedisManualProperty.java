package com.niuchaoqun.springboot.redis.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "redis-manual")
@Data
public class RedisManualProperty {
    private String host;
    private Integer port;
    private Integer database;
    private String password;

    private RedisProperties.Lettuce lettuce;
}
