package com.niuchaoqun.springboot.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
@Slf4j
public class BasicController {
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
