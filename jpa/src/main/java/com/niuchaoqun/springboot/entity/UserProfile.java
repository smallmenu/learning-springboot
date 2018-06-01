package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class UserProfile implements Serializable {
    @Id
    @JsonIgnore
    private Long userId;

    @Column(nullable = true)
    private String job;
}
