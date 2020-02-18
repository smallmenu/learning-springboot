package com.niuchaoqun.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    public static final String EXCHANGE_NAME = "direct_logs";

    @Bean
    Queue queueDirectA() {
        return QueueBuilder.durable().exclusive().autoDelete().build();
    }

    @Bean
    Queue queueDirectB() {
        return new Queue("springboot-direct", true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding bindingDirectA(Queue queueDirectA, DirectExchange exchange) {
        return BindingBuilder.bind(queueDirectA).to(exchange).with("info");
    }

    @Bean
    Binding bindingDirectB(Queue queueDirectB, DirectExchange exchange) {
        return BindingBuilder.bind(queueDirectB).to(exchange).with("error");
    }
}
