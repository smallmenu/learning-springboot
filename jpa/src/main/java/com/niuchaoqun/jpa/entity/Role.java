package com.niuchaoqun.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = "users")
public class Role {
    @Id
    @GeneratedValue
    private Short id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private List<User> users;
}
