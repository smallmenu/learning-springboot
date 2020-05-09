package com.niuchaoqun.springboot.mybatis.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "hello, mybatis common mapper";
    }
}
