package com.niuchaoqun.springboot.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "security index";
    }
}
