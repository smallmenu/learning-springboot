package com.niuchaoqun.springboot.security.controller.api;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "ignore")
@RestController
@RequestMapping("/api/ignore")
public class ApiIgnoreController {

    @ApiOperation("ignore")
    @GetMapping("")
    public RestResult index() {
        return RestResponse.success("ignore");
    }
}
