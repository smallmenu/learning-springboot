package com.niuchaoqun.springboot.rabbitmq.sender;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.suosi.commons.helper.Static.date;

@Component
public class DirectSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public void send() {
        String datetime = date();

        String message = "Direct Hello World! ";

        System.out.println("Direct Send <" + message + datetime +  ">");

        rabbitTemplate.convertAndSend(directExchange.getName(), "info", message + datetime);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        datetime = date();

        rabbitTemplate.convertAndSend(directExchange.getName(), "error", message + datetime);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rabbitTemplate.convertAndSend(directExchange.getName(), "other", message + datetime);
    }
}
