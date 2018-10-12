package com.niuchaoqun.springboot.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class FanoutReceiver {
    /**
     * 这是个表达式，指定 Bean
     *
     * 也可以使用 ${a.b} 接收参数变量
     *
     * @param message
     */
    @RabbitListener(queues = "#{queueFanoutA.name}")
    public void processA(String message) {
        System.out.println("Received FanoutA <" + message + ">");
    }

    @RabbitListener(queues = "#{queueFanoutB.name}")
    public void processB(String message) {
        System.out.println("Received FanoutB <" + message + ">");
    }

    @RabbitListener(queues = "#{queueFanoutC.name}")
    public void processC(String message, Message m, Channel c) throws IOException {
        // 手动确认
        c.basicAck(m.getMessageProperties().getDeliveryTag(), false);

        System.out.println("Received FanoutC <" + message + ">");
    }
}
