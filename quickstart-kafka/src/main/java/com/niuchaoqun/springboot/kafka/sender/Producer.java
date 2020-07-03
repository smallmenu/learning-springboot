package com.niuchaoqun.springboot.kafka.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.github.suosi.commons.helper.Static.date;

@Service
public class Producer {
    private static final String TOPIC = "test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        String datetime = date();

        String message = "Kafka Hello World! ";

        System.out.println("Kafka Send <" + message + datetime +  ">");

        kafkaTemplate.send(TOPIC, message + datetime);
    }
}
