package com.niuchaoqun.springboot.rabbitmq;

import com.niuchaoqun.springboot.rabbitmq.sender.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() {
        helloSender.send();
    }
}
