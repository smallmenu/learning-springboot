package com.niuchaoqun.springboot.security.controller.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Basic")
@RestController
@RequestMapping("/basic")
@Slf4j
public class BasicController {
    @ApiOperation("test1")
    @GetMapping("test1")
    @ResponseBody
    public String test1() {
        return "http basic test1";
    }

    @ApiOperation("test2")
    @GetMapping("test2")
    @ResponseBody
    public String test2() {
        return "http basic test2";
    }
}
