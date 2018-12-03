package com.niuchaoqun.springboot.core;

/**
 * @author niuchaoqun
 */
public final class RestResponse {
    public static <T> RestResult<T> success() {
        return new RestResult<T>().setState(true);
    }

    public static <T> RestResult<T> success(String message) {
        return new RestResult<T>().setState(true).setMessage(message);
    }

    public static <T> RestResult<T> fail() {
        return new RestResult<T>().setState(false);
    }

    public static <T> RestResult<T> fail(String error) {
        return new RestResult<T>().setState(false).setError(error);
    }

    public static <T> RestResult<T> data(T data) {
        return new RestResult<T>().setState(true).setData(data);
    }
}
