package com.niuchaoqun.springboot.redis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Product implements Serializable {
    @Id
    private Long id;

    private String name;

    @JsonFormat()
    private Double price;
}
