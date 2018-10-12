package com.niuchaoqun.springboot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot RabbitMQ 配置
 *
 * 一个应用可以有多个配置，但是 Bean （queue/binding/exchange） 的名称不能相同
 *
 *
 * @author SuoSi
 */
@Configuration
public class HelloConfig {
    public static final String QUEUE_NAME = "springboot-hello";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}
