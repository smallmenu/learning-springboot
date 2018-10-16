package com.niuchaoqun.springboot;

import com.niuchaoqun.springboot.job.HelloJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class Test implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("test run");

        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail job = newJob(HelloJob.class).withIdentity("job", "group").build();

        SimpleTrigger trigger = newTrigger().withIdentity("trigger", "group")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);

        System.out.println();
    }
}
