package com.niuchaoqun.springboot.kafka.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class Producer {
    private static final String TOPIC = "test";

    @Value("${kafka.producer.concurrency:1}")
    private Integer concurrency;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static AtomicLong count = new AtomicLong();

    public void send() {
        if (concurrency > 0) {
            ExecutorService executors = Executors.newFixedThreadPool(concurrency);

            try {
                while (true) {
                    ProducerJob producerJob = new ProducerJob(kafkaTemplate, TOPIC);
                    executors.execute(producerJob);
                }

            } finally {
                executors.shutdown();
            }
        }
    }
}
