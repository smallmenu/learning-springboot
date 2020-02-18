package com.niuchaoqun.springboot.security.dto.form;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AdminAddForm {
    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9.\\-_]{4,31}", message = "用户名不符合要求")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,32}$", message = "密码不符合要求")
    private String password;

    @NotNull
    @Size(min = 2, max = 32)
    private String name;
}
