package com.niuchaoqun.springboot.redis.property;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuchaoqun
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisDefaultProperty {
    private String host;
    private Integer port;
    private Integer database;
    private String password;

    private RedisProperties.Cluster cluster;

    private RedisProperties.Lettuce lettuce;
}
