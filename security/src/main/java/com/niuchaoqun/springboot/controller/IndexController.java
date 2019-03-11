package com.niuchaoqun.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuchaoqun
 */
@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "security index";
    }

    @RequestMapping("/basic")
    @ResponseBody
    public String basic() {
        return "http basic";
    }
}
