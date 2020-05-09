package com.niuchaoqun.springboot.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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
 * Springboot 自动装配的 RedisConnectionFactory 会自动读取 spring.redis.* 配置以及连接池的设定，进而自动装配 RedisTemplate 这些 Bean
 * 如果只需要操作 1 个 Redis 实例，只需要对默认装配做一些属性行为的变更，可以如本类这样进行简单的配置。
 * <p>
 * 如果我们需要操作多个 Redis 实例，那么想当然的会增加一段新的配置，然后手动注册 RedisConnectionFactory、RedisTemplate 的 Bean
 * 比如在 RedisManualConfig.class 中，我们注册了新的连接、新的 RedisTemplate Bean 等，并且给了不同的 name，
 * 注入时使用 @Qualifier("manualRedisTemplate") 来使用自定义的 Redis 实例。
 * <p>
 * 然后。这时候会出现一个问题，默认注入的 RedisTemplate 与自定义的 @Qualifier("manualRedisTemplate") RedisConnectionFactory 连接池会串台
 * 两个 RedisTemplate 用的都是 RedisManualConfig 中的 连接池。
 * 很让人困惑，为什么增加了一个自定义的配置，默认装配的连接池竟然都变成自定义的了。
 * <p>
 * 通过查看源码：org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration，中的注解：
 *
 * @ConditionalOnMissingBean(RedisConnectionFactory.class) 发现一旦你手动注册了 RedisConnectionFactory 的 Bean，那么自动装配的连接池就不会生效。
 * <p>
 * 为什么默认的 RedisTemplate 还能注册成功呢？因为在
 * org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration 中的注解条件只是 name：
 * @ConditionalOnMissingBean(name = "redisTemplate")
 * <p>
 * 解决方案：
 * 一旦你要操作多个实例，那么你应该对每个实例都进行手动配置，同时还有给其中注解上：  @Primary
 * 因为一些配套的组件比如使用 Redis Session 会使用默认的 RedisConnectionFactory 进行装配
 */
//@Configuration
//public class RedisConfig {
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
//     * 有专门的序列化机制，使用的是 StringRedisSerializer
//     * <p>
//     * 注意：在本配置中，它和 RedisTemplate 不互通，因为配置了 RedisTemplate 使用 Jackson 来对 Value 进行序列化，获取 StringRedisTemplate 设置的值，获取会报错。
//     *
//     * @param redisConnectionFactory
//     * @return
//     */
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        return stringRedisTemplate;
//    }
//}
