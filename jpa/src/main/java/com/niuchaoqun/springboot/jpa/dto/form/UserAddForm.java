package com.niuchaoqun.springboot.jpa.dto.form;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserAddForm {
    @NotEmpty
    @Size(min = 2, max = 32)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 32)
    @Email
    private String username;

    @NotEmpty
    @Size(min = 6, max = 32, message = "长度不符合要求")
    private String password;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String birthday;

    @NotEmpty
    @Pattern(regexp = "(male|female)")
    private String sex;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")
    private String access;

    @Pattern(regexp = "\\d{2}:\\d{2}:\\d{2}")
    private String access_time;

    @Min(value = 1)
    @Max(value = 255)
    private Short role_id;

    private String address;

    private String job;
}
