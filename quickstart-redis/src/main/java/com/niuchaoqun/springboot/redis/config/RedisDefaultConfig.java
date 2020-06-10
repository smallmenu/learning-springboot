package com.niuchaoqun.springboot.redis.config;

import com.niuchaoqun.springboot.redis.property.RedisDefaultProperty;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
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


//@Configuration
//public class RedisDefaultConfig {
//    @Autowired
//    private RedisDefaultProperty redisDefaultProperty;
//
//    @Primary
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        LettuceConnectionFactory lettuceConnectionFactory;
//
//        // Lettuce 连接池参数
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(Optional.ofNullable(redisDefaultProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMaxActive)
//                .orElse(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL));
//        poolConfig.setMaxIdle(Optional.ofNullable(redisDefaultProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMaxIdle).orElse(GenericObjectPoolConfig.DEFAULT_MAX_IDLE));
//        poolConfig.setMinIdle(Optional.ofNullable(redisDefaultProperty.getLettuce())
//                .map(RedisProperties.Lettuce::getPool)
//                .map(RedisProperties.Pool::getMinIdle).orElse(GenericObjectPoolConfig.DEFAULT_MIN_IDLE));
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .poolConfig(poolConfig)
//                .build();
//
//        // Redis 配置，兼容单机和集群
//        if (redisDefaultProperty.getCluster() == null) {
//            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//            config.setHostName(redisDefaultProperty.getHost());
//            config.setPort(redisDefaultProperty.getPort());
//            config.setDatabase(redisDefaultProperty.getDatabase());
//            config.setPassword(RedisPassword.of(redisDefaultProperty.getPassword()));
//
//            lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfig);
//        } else {
//            RedisClusterConfiguration config = new RedisClusterConfiguration(redisDefaultProperty.getCluster().getNodes());
//            config.setPassword(RedisPassword.of((redisDefaultProperty.getPassword())));
//            lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfig);
//        }
//
//        return lettuceConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
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
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        return stringRedisTemplate;
//    }
//}
