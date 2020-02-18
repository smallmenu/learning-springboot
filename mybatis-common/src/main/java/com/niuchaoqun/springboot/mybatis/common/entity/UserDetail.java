package com.niuchaoqun.springboot.mybatis.common.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserDetail implements Serializable {
    @Id
    private Long id;

    private Long userId;

    private String address;
}
