package com.niuchaoqun.springboot.mybatis.common.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Order {
    @Id
    private Long id;

    private String name;
}
