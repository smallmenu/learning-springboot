package com.niuchaoqun.springboot.job;

import com.niuchaoqun.springboot.State;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DisallowConcurrentExecution 注解，禁止给定 Job 定义的多个实例并发执行
 */
@DisallowConcurrentExecution
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String name = jobDetail.getKey().toString();


        long l = State.counter.incrementAndGet();
        System.out.println(l + Thread.currentThread().getName() + " Start <"+ name +"> " + datetime);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
