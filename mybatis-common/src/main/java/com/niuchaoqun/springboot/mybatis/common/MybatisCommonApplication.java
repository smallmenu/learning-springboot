package com.niuchaoqun.springboot.mybatis.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.niuchaoqun.springboot.mapper")
public class MybatisCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisCommonApplication.class, args);
    }
}
