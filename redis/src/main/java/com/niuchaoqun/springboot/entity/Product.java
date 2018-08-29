package com.niuchaoqun.springboot.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Product implements Serializable {
    @Id
    private Long id;

    private String name;

    private Double price;
}
