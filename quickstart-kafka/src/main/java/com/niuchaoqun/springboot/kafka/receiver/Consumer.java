package com.niuchaoqun.springboot.kafka.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuchaoqun.springboot.kafka.dto.NginxLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class Consumer {
    private static final AtomicLong count = new AtomicLong();

    @KafkaListener(topics = "test", autoStartup = "${kafka.listener.auto-startup:false}")
    public void consume(String message, Acknowledgment ack) {
        long current = count.getAndIncrement();
        log.info("Consumed message -> {}", current);

        ObjectMapper mapper = new ObjectMapper();
        try {
            NginxLog nginxLog = mapper.readValue(message, NginxLog.class);
            log.debug(nginxLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ack.acknowledge();
    }
}
