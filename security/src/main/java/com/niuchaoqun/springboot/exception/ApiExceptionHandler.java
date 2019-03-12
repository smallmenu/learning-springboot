package com.niuchaoqun.springboot.exception;

import com.niuchaoqun.springboot.rest.RestResponse;
import com.niuchaoqun.springboot.rest.RestResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public RestResult jsonErrorHandler(HttpServletRequest request, ApiException e){
        return RestResponse.fail(e.getMessage());
    }
}
