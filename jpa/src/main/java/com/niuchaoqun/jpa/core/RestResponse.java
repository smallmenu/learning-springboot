package com.niuchaoqun.jpa.core;

public class RestResponse {
    private final static String SUCCESS = "success";

    public static <T> RestResult<T> makeOKRsp() {
        return new RestResult<T>().setMsg(SUCCESS);
    }

    public static <T> RestResult<T> makeOKRsp(T data) {
        return new RestResult<T>().setMsg(SUCCESS).setData(data);
    }
}
