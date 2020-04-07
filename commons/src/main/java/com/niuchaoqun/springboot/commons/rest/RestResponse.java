package com.niuchaoqun.springboot.commons.rest;


public final class RestResponse {
    public static <T> RestResult<T> error() {
        return new RestResult<T>().setCode(-1);
    }

    public static <T> RestResult<T> error(Integer code) {
        return new RestResult<T>().setCode(code).setState(false);
    }

    public static <T> RestResult<T> error(Integer code, String error) {
        return new RestResult<T>().setCode(code).setState(false).setError(error);
    }

    public static <T> RestResult<T> success() {
        return new RestResult<T>().setCode(0).setState(true);
    }

    public static <T> RestResult<T> success(String message) {
        return new RestResult<T>().setCode(0).setState(true).setMessage(message);
    }

    public static <T> RestResult<T> fail() {
        return new RestResult<T>().setCode(0).setState(false);
    }

    public static <T> RestResult<T> fail(String error) {
        return new RestResult<T>().setCode(0).setState(false).setError(error);
    }

    public static <T> RestResult<T> data(T data) {
        return new RestResult<T>().setCode(0).setState(true).setData(data);
    }
}
