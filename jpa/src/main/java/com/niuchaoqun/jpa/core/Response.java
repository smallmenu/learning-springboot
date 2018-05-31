package com.niuchaoqun.jpa.core;

import java.util.HashMap;

public class Response {
    private static HashMap<String, Object> response = null;

    public static HashMap<String, Object> success() {
        response = new HashMap<>();
        response.put("state", true);

        return response;
    }

    public static HashMap<String, Object> success(String message) {
        response = new HashMap<>();
        response.put("state", true);
        response.put("message", message);

        return response;
    }

    public static HashMap<String, Object> error() {
        response = new HashMap<>();
        response.put("state", false);

        return response;
    }

    public static HashMap<String, Object> error(String error) {
        response = new HashMap<>();
        response.put("state", false);
        response.put("error", error);

        return response;
    }

    public static HashMap<String, Object> data(Object data) {
        response = new HashMap<>();
        response.put("state", true);
        response.put("data", data);

        return response;
    }
}
