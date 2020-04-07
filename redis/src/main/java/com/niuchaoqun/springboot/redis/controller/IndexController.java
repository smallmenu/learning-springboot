package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.redis.property.RedisManualProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.suosi.commons.helper.Static.date;


@RestController
@Slf4j
public class IndexController extends BaseController {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisManualProperty redisManualProperty;

    @GetMapping("/")
    public String index() {
        return "hello, redis";
    }

    @GetMapping("/message")
    public void message() {
        String message = date();
        for (int i = 0; i < 1000000; i++) {
            template.convertAndSend("chat", message + " " + i);
        }
    }
}
