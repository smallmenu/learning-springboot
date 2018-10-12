package com.niuchaoqun.springboot.rabbitmq;

import com.niuchaoqun.springboot.sender.DirectSender;
import com.niuchaoqun.springboot.sender.FanoutSender;
import com.niuchaoqun.springboot.sender.HelloSender;
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

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private DirectSender directSender;

    @Test
    public void hello() {
        helloSender.send();
    }

    @Test
    public void fanout() {
        fanoutSender.send();
    }

    @Test
    public void direct() {
        directSender.send();
    }
}
