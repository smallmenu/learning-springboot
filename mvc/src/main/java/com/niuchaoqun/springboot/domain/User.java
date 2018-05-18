package com.niuchaoqun.springboot.domain;

import lombok.Data;

@Data
public class User {
    private long id;
    private String name;

    public User() {

    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
