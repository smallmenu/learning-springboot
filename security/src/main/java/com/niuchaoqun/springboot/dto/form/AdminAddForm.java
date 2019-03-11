package com.niuchaoqun.springboot.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AdminAddForm {
    @ApiModelProperty(value = "用户名，5-32长度，英文开头", required = true, dataType = "String")
    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9.\\-_]{4,31}", message = "用户名不符合要求")
    private String username;

    @ApiModelProperty(value = "密码，6-32长度，至少大小写加数字", required = true, dataType = "String")
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,32}$", message = "密码不符合要求")
    private String password;

    @ApiModelProperty(value = "姓名", required = true, dataType = "String")
    @NotNull
    @Size(min = 2, max = 32)
    private String name;
}
