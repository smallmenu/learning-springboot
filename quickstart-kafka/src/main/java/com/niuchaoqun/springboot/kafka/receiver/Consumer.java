package com.niuchaoqun.springboot.kafka.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class Consumer {
    private static final AtomicLong count = new AtomicLong();

    @KafkaListener(topics = "test")
    public void consume(String message, Acknowledgment ack) {
        long current = count.getAndIncrement();
        log.info("Consumed message -> {}", current);
        ack.acknowledge();
    }
}
