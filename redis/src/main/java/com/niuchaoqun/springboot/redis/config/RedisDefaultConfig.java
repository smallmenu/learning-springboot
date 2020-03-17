package com.niuchaoqun.springboot.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author niuchaoqun
 */
@Configuration
public class RedisDefaultConfig {
    @Value("${spring.redis.host:}")
    private String host;

    @Value("${spring.redis.port:}")
    private Integer port;

    @Value("${spring.redis.database:}")
    private Integer database;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.lettuce.pool.max-active:}")
    private Integer lettuceMaxActive;

    @Value("${spring.redis.lettuce.pool.max-idle:}")
    private Integer lettuceMaxIdle;

    @Value("${spring.redis.host.pool.min-idle:}")
    private Integer lettuceMinIdle;

    @Primary
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);
        config.setPassword(password);

        // Lettuce默认连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(Optional.ofNullable(lettuceMaxActive).orElse(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL));
        poolConfig.setMaxIdle(Optional.ofNullable(lettuceMaxIdle).orElse(GenericObjectPoolConfig.DEFAULT_MAX_IDLE));
        poolConfig.setMinIdle(Optional.ofNullable(lettuceMinIdle).orElse(GenericObjectPoolConfig.DEFAULT_MIN_IDLE));
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfig);

        return lettuceConnectionFactory;
    }

    @Primary
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
