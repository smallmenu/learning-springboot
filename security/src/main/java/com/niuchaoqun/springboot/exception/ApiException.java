package com.niuchaoqun.springboot.exception;

public class ApiException extends Exception  {

    public ApiException(String message) {
        super(message);
    }
}
