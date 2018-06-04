package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class User {

    private Long id;

    private String name;

    @JsonProperty(value = "email")
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    private LocalDate birthday;

    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime access;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime access_time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    private Integer state;
}
