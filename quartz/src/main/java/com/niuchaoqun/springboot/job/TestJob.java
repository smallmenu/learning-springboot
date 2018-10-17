package com.niuchaoqun.springboot.job;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String name = jobDetail.getKey().toString();
        System.out.println(Thread.currentThread().getName() + " Start <"+ name +"> " + datetime);

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
