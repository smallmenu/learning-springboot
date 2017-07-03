package com.niuchaoqun.jpa.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
public class UserAddDto {
    @NotEmpty
    @Length(min = 2, max = 32)
    private String name;

    @NotEmpty
    @Length(min = 3, max = 32)
    @Email
    private String username;

    @NotEmpty
    @Length(min = 6, max = 32, message = "长度不符合要求")
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

    @NotEmpty
    private String role_id;

    private String address;
}
