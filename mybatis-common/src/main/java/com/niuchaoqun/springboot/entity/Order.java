package com.niuchaoqun.springboot.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Order {
    @Id
    private Long id;

    private String name;
}
