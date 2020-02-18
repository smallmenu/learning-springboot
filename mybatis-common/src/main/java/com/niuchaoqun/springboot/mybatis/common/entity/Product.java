package com.niuchaoqun.springboot.mybatis.common.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Product {
    @Id
    private Long id;

    private String name;

    private Double price;
}
