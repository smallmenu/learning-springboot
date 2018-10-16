package com.niuchaoqun.springboot;

import com.niuchaoqun.springboot.job.TestJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("test run");

        Random random = new Random(System.currentTimeMillis());

        for (int i = 10; i < 40; i++) {
            JobDetail jobExist = scheduler.getJobDetail(JobKey.jobKey("job" + i, "group"));

            if (jobExist == null) {
                JobDetail job = newJob(TestJob.class).withIdentity("job" + i, "group").build();

                SimpleTrigger trigger = newTrigger().withIdentity("trigger" + i, "group")
                        .startNow()
                        .withSchedule(simpleSchedule().withIntervalInMilliseconds(300 + random.nextInt(200)).repeatForever())
                        .build();

                scheduler.scheduleJob(job, trigger);
            }
        }

        scheduler.start();

        System.out.println();
    }
}
