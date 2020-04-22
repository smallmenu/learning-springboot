package com.niuchaoqun.springboot.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class IndexController {
    @GetMapping("")
    @ResponseBody
    public String index() {
        return "security index";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "security test";
    }
}
