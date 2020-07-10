package com.niuchaoqun.springboot.kafka;

import com.niuchaoqun.springboot.kafka.sender.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private Producer producer;

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            String daemon = args[0];

            switch (daemon) {
                // 启动
                case "producer":
                    producer.send();
                    break;
                default:
                    break;
            }
        }
    }
}
