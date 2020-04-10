package com.niuchaoqun.springboot.security.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class AdminEditForm {
    @ApiModelProperty(value = "角色ID", required = false, dataType = "String")
    private Long roleId;

    @ApiModelProperty(value = "姓名", required = false, dataType = "String")
    @Size(min = 2, max = 32)
    private String name;

    @ApiModelProperty(value = "密码，字母大小写加数字，6-32长度", required = false, dataType = "String")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,32}$", message = "密码不符合要求")
    private String password;

    @ApiModelProperty(value = "Email", required = false, dataType = "String")
    @Email
    private String email;

    @ApiModelProperty(value = "锁定状态", required = false, dataType = "Integer")
    @PositiveOrZero
    private Integer locked;

    @ApiModelProperty(value = "启用状态", required = false, dataType = "Integer")
    @PositiveOrZero
    private Integer state;

    private static AdminEditForm EMPTY = new AdminEditForm();

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}
