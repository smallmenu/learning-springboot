package com.niuchaoqun.springboot.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * Springboot 自动装配的 RedisConnectionFactory 会自动读取 spring.redis.* 配置以及连接池设定
 * <p>
 * 如果我们需要操作多个Redis实例，那么需要对默认的 RedisTemplate 也进行手动配置，参考 RedisDefaultConfig
 * 否则的话，RedisConnectionFactory 会串号。
 * <p>
 * 举例：如果我们配置了 RedisConfig 和 RedisManualConfig 那么，默认的 RedisConnectionFactory 实际是 RedisManualConfig 中的连接
 *
 * @author niuchaoqun
 */
//@Configuration
public class RedisConfig {
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
//    /**
//     * StringRedisTemplate 继承自 RedisTemplate<String, String>
//     * 是一个独特的序列化机制，使用的是 StringRedisSerializer，
//     *
//     * 它和 RedisTemplate 不互通
//     *
//     * @param redisConnectionFactory
//     * @return
//     */
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        return stringRedisTemplate;
//    }
}
