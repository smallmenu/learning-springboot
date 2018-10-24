package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class IndexController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/")
    public String index() {
        return "hello, redis";
    }

    @RequestMapping("/message")
    public void message() {

        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for (int i = 0; i < 5; i++) {
            template.convertAndSend("chat", message+ " " + i);
        }
    }
}
