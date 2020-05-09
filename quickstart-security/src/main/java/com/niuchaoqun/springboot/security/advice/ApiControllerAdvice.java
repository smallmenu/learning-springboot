package com.niuchaoqun.springboot.security.advice;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获
 */
@ControllerAdvice("com.niuchaoqun.springboot.security.controller.api")
@Slf4j
public class ApiControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResult errorHandler(Exception ex) {
        return RestResponse.error(500, ex.getLocalizedMessage());
    }
}
