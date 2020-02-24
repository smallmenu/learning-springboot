package com.niuchaoqun.springboot.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuchaoqun
 */
@RestController
@Slf4j
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "security index";
    }
}
