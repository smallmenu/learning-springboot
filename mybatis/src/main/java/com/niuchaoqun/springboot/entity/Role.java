package com.niuchaoqun.springboot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private Short id;

    private String name;
}
