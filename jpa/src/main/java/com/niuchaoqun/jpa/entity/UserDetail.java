package com.niuchaoqun.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "user")
public class UserDetail {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    private String address;

    @OneToOne
    @JoinColumn
    @JsonBackReference
    private User user;
}
