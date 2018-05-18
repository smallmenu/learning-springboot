package com.niuchaoqun.jpa.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.HashMap;

public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private HashMap<String, Object> response = null;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    protected HashMap<String, Object> responseSuccess() {
        response = new HashMap<>();
        response.put("state", true);

        return response;
    }

    protected String resultErrors(BindingResult result) {
        FieldError fieldError = result.getFieldError();
        return fieldError.getField() + fieldError.getDefaultMessage();
    }

    protected HashMap<String, Object> responseSuccess(String message) {
        response = new HashMap<>();
        response.put("state", true);
        response.put("message", message);

        return response;
    }

    protected HashMap<String, Object> responseError(String error) {
        response = new HashMap<>();
        response.put("state", false);
        response.put("error", error);

        return response;
    }

    protected HashMap<String, Object> responseError() {
        response = new HashMap<>();
        response.put("state", false);

        return response;
    }

    protected HashMap<String, Object> responseData(Object data) {
        response = new HashMap<>();
        response.put("state", true);
        response.put("data", data);

        return response;
    }
}
