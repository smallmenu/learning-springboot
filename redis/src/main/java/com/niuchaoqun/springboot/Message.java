package com.niuchaoqun.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Message implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Message.class);

    @Autowired
    StringRedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
            logger.info("Sending message");

            template.convertAndSend("chat", "Hello from Redis");


    }
}
