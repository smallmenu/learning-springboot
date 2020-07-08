package com.niuchaoqun.springboot.kafka.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class ProducerJob implements Runnable {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topic;

    public ProducerJob(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            String message = ProducerMessage.log();
            if (message != null) {
                kafkaTemplate.send(topic, message);
                long count = Producer.count.getAndIncrement();
                if (count % 10000 == 0) {
                    log.info("Send Message {}, {}", count, message.length());
                }
            }
        }
    }
}
