package com.niuchaoqun.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Autowired
    private ScheduleJobFactory jobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 调度启动延迟
        schedulerFactoryBean.setStartupDelay(11);
        // 覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        // 数据源
        schedulerFactoryBean.setDataSource(dataSource);
        // 使用Spring管理
        schedulerFactoryBean.setJobFactory(jobFactory);
        // 调度器上下文
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");

        schedulerFactoryBean.setSchedulerName("TASK_EXECUTOR");

        return schedulerFactoryBean;
    }
}
