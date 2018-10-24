package com.niuchaoqun.springboot.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    @Scheduled(fixedDelay = 1000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        RunnableThread runnableThread = new RunnableThread();
        Thread thread = new Thread(runnableThread);
        thread.start();
    }

    public static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("RunnableThread:" + Thread.currentThread().getName() + " running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
