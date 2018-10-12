package com.niuchaoqun.springboot.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DirectReceiver {
    @RabbitListener(queues = "#{queueDirectA.name}")
    public void processA(String message, Message m, Channel c) throws IOException {
        // 手动确认
        c.basicAck(m.getMessageProperties().getDeliveryTag(), false);

        System.out.println("Received DirectA <" + m.getMessageProperties().getReceivedRoutingKey() +"> <" + message + ">");
    }

    @RabbitListener(queues = "#{queueDirectB.name}")
    public void processB(String message, Message m, Channel c) throws IOException {
        // 手动确认
        c.basicAck(m.getMessageProperties().getDeliveryTag(), false);

        System.out.println("Received DirectB <" + m.getMessageProperties().getReceivedRoutingKey() +"> <" + message + ">");
    }
}
