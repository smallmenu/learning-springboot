package com.niuchaoqun.springboot;

import com.niuchaoqun.springboot.config.AppProperties;
import com.niuchaoqun.springboot.config.MysqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(HelloApplication.class);

	@Autowired
	private MysqlProperties mysqlProperties;

	@Autowired
	private AppProperties appProperties;

	public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.info(mysqlProperties.toString());
		logger.info(appProperties.toString());

		System.out.println(mysqlProperties.toString());
		System.out.println(appProperties.toString());
	}
}