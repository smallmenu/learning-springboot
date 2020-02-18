package com.niuchaoqun.springboot.commons.base;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author niuchaoqun
 */
public class BaseController {

    protected Integer pageSize = 15;

    /**
     * 参数tirm
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(false);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    /**
     * 错误验证输出
     *
     * @param result
     * @return
     */
    protected String resultErrors(BindingResult result) {
        FieldError fieldError = result.getFieldError();
        return fieldError.getField() + fieldError.getDefaultMessage();
    }
}
