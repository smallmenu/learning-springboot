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

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("管理员用户名")
    private String username;

    @ApiModelProperty("管理员名称")
    private String name;

    @JsonIgnore
    private String password;

    @ApiModelProperty("Email")
    private String email;

    @Column(insertable = false)
    private Integer state;

    @ApiModelProperty("最后登录IP")
    @Column(insertable = false)
    private String lastLoginIp;

    @ApiModelProperty("最后登录时间")
    @Column(insertable = false)
    private LocalDateTime lastLogined;

    @Column(insertable = false)
    protected Long createdby;

    @Column(insertable = false)
    protected Long updatedby;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    protected LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = false, updatable = false)
    protected LocalDateTime updated;

    private Role role;
}
