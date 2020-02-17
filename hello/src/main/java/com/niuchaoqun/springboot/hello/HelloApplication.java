package com.niuchaoqun.springboot.hello;

import com.niuchaoqun.springboot.hello.config.AppProperties;
import com.niuchaoqun.springboot.hello.config.CustomProperties;
import com.niuchaoqun.springboot.hello.config.MysqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Autowired
    private MysqlProperties mysqlProperties;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private CustomProperties customProperties;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug(mysqlProperties.toString());
        logger.debug(appProperties.toString());
        logger.debug(customProperties.toString());

        System.out.println(mysqlProperties.toString());
        System.out.println(appProperties.toString());
        System.out.println(customProperties.toString());
    }
}