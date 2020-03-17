package com.niuchaoqun.springboot.rabbitmq.sender;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.suosi.commons.helper.Static.date;

@Component
public class FanoutSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutExchange;

    public void send() {
        String datetime = date();

        String message = "Fanout Hello World! " + datetime;

        System.out.println("Fanout Send <" + message + ">");

        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);
    }
}
