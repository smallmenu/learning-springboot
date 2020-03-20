package com.niuchaoqun.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message");

        template.convertAndSend("chat", "Hello from Redis");
    }
}
