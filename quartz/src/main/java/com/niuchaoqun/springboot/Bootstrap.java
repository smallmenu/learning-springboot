package com.niuchaoqun.springboot;

import com.niuchaoqun.springboot.job.TestJob;
import org.quartz.*;
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
        String jobPrefix = "job";
        if (args.getSourceArgs().length > 0) {
            System.out.println(args.getSourceArgs()[0]);
            jobPrefix = args.getSourceArgs()[0];
        }

        Random random = new Random(System.currentTimeMillis());

        // 任务总数
        int jobTotal = 1;
        for (int i = 1; i <= jobTotal; i++) {
            String jobName = jobPrefix + i;
            String tiggerName = jobName + "Trigger" + i;
            JobDetail jobExist = scheduler.getJobDetail(JobKey.jobKey(jobName, "group"));

            if (jobExist == null) {
                JobDetail job = newJob(TestJob.class).withIdentity(jobName, "group").storeDurably().build();

                SimpleTrigger trigger = newTrigger().withIdentity(tiggerName, "group")
                        .startNow()
                        .withSchedule(simpleSchedule().withIntervalInSeconds(1+i).repeatForever())
                        .build();

                scheduler.scheduleJob(job, trigger);
            }
        }

        System.out.println();
    }
}
