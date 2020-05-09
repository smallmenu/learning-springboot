package com.niuchaoqun.springboot.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    public static final String EXCHANGE_NAME = "logs";

    @Bean
    Queue queueFanoutA() {
        return QueueBuilder.durable().exclusive().autoDelete().build();
    }

    @Bean
    Queue queueFanoutB() {
        return QueueBuilder.durable().exclusive().autoDelete().build();
    }

    @Bean
    Queue queueFanoutC() {
        return new Queue("springboot-fanout", true);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME, true, false);
    }

    /**
     * 这个绑定可以自动Bean注入，也可以通过 Qualifier 注解
     *
     * @param queue
     * @param e
     * @return
     */
    @Bean
    Binding bindingFanoutA(@Qualifier("queueFanoutA") Queue queue, @Qualifier("fanoutExchange") FanoutExchange e) {
        return BindingBuilder.bind(queue).to(e);
    }

    @Bean
    Binding bindingFanoutB(Queue queueFanoutB, FanoutExchange exchange) {
        return BindingBuilder.bind(queueFanoutB).to(exchange);
    }

    @Bean
    Binding bindingFanoutC(Queue queueFanoutC, FanoutExchange exchange) {
        return BindingBuilder.bind(queueFanoutC).to(exchange);
    }
}
