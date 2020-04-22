package com.niuchaoqun.springboot.redis.config;

import com.niuchaoqun.springboot.redis.property.RedisManualProperty;
import com.niuchaoqun.springboot.redis.property.RedisOtherProperty;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
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
 * 自定义 Redis 实例配置
 *
 *
 */
@Configuration
public class RedisOtherConfig {
    @Autowired
    private RedisOtherProperty redisOtherProperty;

    @Bean(name = "otherRedisConnectionFactory")
    public RedisConnectionFactory otherRedisConnectionFactory() {
        // Lettuce 连接池参数
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(Optional.ofNullable(redisOtherProperty.getLettuce())
                .map(RedisProperties.Lettuce::getPool)
                .map(RedisProperties.Pool::getMaxActive)
                .orElse(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL));
        poolConfig.setMaxIdle(Optional.ofNullable(redisOtherProperty.getLettuce())
                .map(RedisProperties.Lettuce::getPool)
                .map(RedisProperties.Pool::getMaxIdle).orElse(GenericObjectPoolConfig.DEFAULT_MAX_IDLE));
        poolConfig.setMinIdle(Optional.ofNullable(redisOtherProperty.getLettuce())
                .map(RedisProperties.Lettuce::getPool)
                .map(RedisProperties.Pool::getMinIdle).orElse(GenericObjectPoolConfig.DEFAULT_MIN_IDLE));
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();

        // Redis 配置
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisOtherProperty.getHost());
        config.setPort(redisOtherProperty.getPort());
        config.setDatabase(redisOtherProperty.getDatabase());
        config.setPassword(RedisPassword.of(redisOtherProperty.getPassword()));

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfig);

        return lettuceConnectionFactory;
    }

    @Bean(name = "otherRedisTemplate")
    public RedisTemplate otherRedisTemplate(@Qualifier("otherRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean(name = "otherStringRedisTemplate")
    public StringRedisTemplate otherStringRedisTemplate(@Qualifier("otherRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);

        return stringRedisTemplate;
    }
}
