package com.niuchaoqun.springboot.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {
    public static final String QUEUE_NAME = "springboot-hello";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}
