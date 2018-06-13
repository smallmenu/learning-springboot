package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 通用 Mapper 表名和字段名默认通过驼峰转下划线形式，可以使用 ，@Column 自定义
 *
 * @Table 表名默认使用驼峰式映射下划线，通过 name 属性自定义
 * @Column 字段名默认使用驼峰式映射下划线，通过 name 属性自定义，同时可以指定 insertable, updatable
 * @Transient 注解可忽略的字段
 * @Id 注解主键
 * @GeneratedValue 指定主键生成形式，以及回写主键数据
 *
 */
@Table(name = "user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    private String name;

    private Short roleId;

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
    private LocalTime accessTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    private LocalDateTime updated;

    private Integer state;

    private Role role;

    private UserProfile profile;

    private UserDetail detail;
}
