package com.niuchaoqun.springboot.jpa.controller;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "hello, spring data springboot";
    }

    @ApiOperation(value = "测试返回值")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public RestResult test() {
        return RestResponse.fail("error");
    }
}
