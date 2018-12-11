package com.niuchaoqun.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;


/**
 * 用于自定义 Quartz 配置，暂时没用
 *
 * @author niuchaoqun
 */
//@Component
public class QuartzConfig {
    @Autowired
    private ScheduleJobFactoryConfig jobFactory;

    /**
     * To Configuration Quartz , not necessary, if not config this, will use default
     *
     * @param dataSource
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // 设置名称
        schedulerFactoryBean.setSchedulerName("QuartzMySQLScheduler");
        // 是否初始化完成后自动调度
        schedulerFactoryBean.setAutoStartup(true);
        // 初始化完成后调度延时
        schedulerFactoryBean.setStartupDelay(2);
        // 数据源
        schedulerFactoryBean.setDataSource(dataSource);
        // 默认为 AdaptableJobFactory
        schedulerFactoryBean.setJobFactory(jobFactory);
        // 配置文件
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));

        return schedulerFactoryBean;
    }
}
