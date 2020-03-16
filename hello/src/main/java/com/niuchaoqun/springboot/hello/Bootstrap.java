package com.niuchaoqun.springboot.hello;

import com.niuchaoqun.springboot.hello.property.CustomProperty;
import com.niuchaoqun.springboot.hello.property.MysqlProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private MysqlProperty mysqlProperty;
    @Autowired
    private CustomProperty customProperty;

    @Override
    public void run(String... args) throws Exception {
        log.debug(mysqlProperty.toString());
        log.debug(customProperty.toString());

        System.out.println(mysqlProperty.toString());
        System.out.println(customProperty.toString());
    }
}
