package com.niuchaoqun.springboot.redis.config;

import com.niuchaoqun.springboot.redis.property.RedisManualProperty;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
 */
//@Configuration
//public class RedisManualConfig {
//    @Autowired
//    private RedisManualProperty redisManualProperty;
//
//    @Bean(name = "manualRedisConnectionFactory")
//    public RedisConnectionFactory manualRedisConnectionFactory() {
//        // Lettuce 连接池参数
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(Optional.ofNullable(redisManualProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMaxActive)
//                .orElse(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL));
//        poolConfig.setMaxIdle(Optional.ofNullable(redisManualProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMaxIdle).orElse(GenericObjectPoolConfig.DEFAULT_MAX_IDLE));
//        poolConfig.setMinIdle(Optional.ofNullable(redisManualProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMinIdle).orElse(GenericObjectPoolConfig.DEFAULT_MIN_IDLE));
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .poolConfig(poolConfig)
//                .build();
//
//        // Redis 配置
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName(redisManualProperty.getHost());
//        config.setPort(redisManualProperty.getPort());
//        config.setDatabase(redisManualProperty.getDatabase());
//        config.setPassword(RedisPassword.of(redisManualProperty.getPassword()));
//
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfig);
//
//        return lettuceConnectionFactory;
//    }
//
//    @Bean(name = "manualRedisTemplate")
//    public RedisTemplate manualRedisTemplate(@Qualifier("manualRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
//
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        redisTemplate.afterPropertiesSet();
//
//        return redisTemplate;
//    }
//
//    @Bean(name = "manualStringRedisTemplate")
//    public StringRedisTemplate manualStringRedisTemplate(@Qualifier("manualRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        return stringRedisTemplate;
//    }
//}
