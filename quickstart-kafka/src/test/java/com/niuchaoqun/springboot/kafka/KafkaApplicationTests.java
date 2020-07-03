package com.niuchaoqun.springboot.kafka;

import com.niuchaoqun.springboot.kafka.sender.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    public void producer() {
        producer.send();
    }
}
