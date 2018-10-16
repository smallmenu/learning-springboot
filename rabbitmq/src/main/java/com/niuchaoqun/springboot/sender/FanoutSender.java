package com.niuchaoqun.springboot.sender;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FanoutSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutExchange;

    public void send() {
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String message = "Hello World! " + datetime;

        System.out.println("Fanout Send <" + message + ">");

        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);
    }
}