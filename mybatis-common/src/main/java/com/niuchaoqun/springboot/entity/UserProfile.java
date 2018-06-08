package com.niuchaoqun.springboot.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserProfile implements Serializable {
    @Id
    private Long userId;

    private String job;
}
