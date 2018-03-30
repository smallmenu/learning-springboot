package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
    @RequestMapping("/rest")
public class RestfulController {

    private final static Logger logger = LoggerFactory.getLogger(RestfulController.class);

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("")
    public String index() {
        return "Hello Rest";
    }

    @RequestMapping("/greeing")
    public User index(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new User(counter.incrementAndGet(), name);
    }


}
