package com.niuchaoqun.springboot.entity;

import lombok.Data;

@Data
public class Order {
    private Long id;

    private String name;

    private Long user_id;
}
