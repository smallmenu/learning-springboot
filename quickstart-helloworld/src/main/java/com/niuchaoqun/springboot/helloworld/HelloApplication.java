package com.niuchaoqun.springboot.helloworld;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
