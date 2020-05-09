package com.niuchaoqun.springboot.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "springboot-hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("Received <" + message + ">");
    }
}
