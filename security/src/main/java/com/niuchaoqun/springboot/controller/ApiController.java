package com.niuchaoqun.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping("test1")
    @ResponseBody
    public String test1() {
        return "http basic test1";
    }

    @RequestMapping("test2")
    @ResponseBody
    public String test2() {
        return "http basic test2";
    }
}