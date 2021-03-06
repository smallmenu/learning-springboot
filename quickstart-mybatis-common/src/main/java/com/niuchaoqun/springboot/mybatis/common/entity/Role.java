package com.niuchaoqun.springboot.mybatis.common.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Role implements Serializable {
    @Id
    private Short id;

    private String name;
}
