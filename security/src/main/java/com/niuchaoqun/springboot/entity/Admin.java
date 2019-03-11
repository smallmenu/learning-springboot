package com.niuchaoqun.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    @ApiModelProperty("管理员用户名")
    private String username;

    @ApiModelProperty("管理员名称")
    private String name;

    @JsonIgnore
    private String password;

    @Column(insertable = false)
    private Integer state;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    protected LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    protected LocalDateTime updated;
}