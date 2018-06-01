package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class User implements Serializable {


    private Long id;

    private String name;

    @JsonProperty(value = "email")
    private String username;

    @JsonIgnore
    private String password;

    private Short roleId;

    @JsonIgnore
    private String salt;

    private LocalDate birthday;

    private String sex;

    private LocalDateTime access;

    private LocalTime accessTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    private Integer state;
}
