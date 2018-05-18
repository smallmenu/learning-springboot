package com.niuchaoqun.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MysqlProperties {
    @Value("${mysql.host:localhost}")
    private String host;

    @Value("${mysql.user:root}")
    private String user;

    @Value("${mysql.mix:localhost/root}")
    private String mix;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    @Override
    public String toString() {
        return "MysqlProperties{" +
                "host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", mix='" + mix + '\'' +
                '}';
    }
}
