package com.niuchaoqun.springboot.security.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author niuchaoqun
 */
@Data
public class AdminAddForm {
    @ApiModelProperty(value = "用户名，字母开头，字母数字下划线组合，4-32长度", required = true, dataType = "String")
    @NotBlank
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_]{3,32}", message = "用户名，字母开头，字母数字下划线组合，4-32长度")
    private String username;

    @ApiModelProperty(value = "密码，6-32长度，至少大小写加数字", required = true, dataType = "String")
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,32}$", message = "密码不符合要求")
    private String password;

    @ApiModelProperty(value = "姓名", required = true, dataType = "String")
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @ApiModelProperty(value = "Email", required = true, dataType = "String")
    @NotBlank
    @Email(message = "Email不合法")
    private String email;

    @ApiModelProperty(value = "角色ID", required = true, dataType = "Long")
    @Positive
    private Long roleId;
}
