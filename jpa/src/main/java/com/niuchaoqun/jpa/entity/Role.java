package com.niuchaoqun.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private Short id;

    @Column(nullable = false)
    private String name;
}
