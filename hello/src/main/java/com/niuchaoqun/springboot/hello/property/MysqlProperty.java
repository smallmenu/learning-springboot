package com.niuchaoqun.springboot.hello.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MysqlProperty {
    @Value("${mysql.host:localhost}")
    private String host;

    @Value("${mysql.user:root}")
    private String user;

    @Value("${mysql.mix:localhost/root}")
    private String mix;
}
