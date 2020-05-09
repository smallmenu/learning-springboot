package com.niuchaoqun.springboot.rabbitmq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.suosi.commons.helper.Static.date;

@Component
public class HelloSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String datetime = date();
        String message = "Hello World! " + datetime;

        System.out.println("Send <" + message + ">");

        rabbitTemplate.convertAndSend("springboot-hello", message);
    }
}
