package com.niuchaoqun.springboot.security.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author niuchaoqun
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> {
    private Integer code;

    private Boolean state;

    private String message;

    private String error;

    private T data;

    public Integer getCode() {
        return code;
    }

    public RestResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getState() {
        return state;
    }

    public RestResult<T> setState(Boolean state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getError() {
        return error;
    }

    public RestResult<T> setError(String error) {
        this.error = error;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
