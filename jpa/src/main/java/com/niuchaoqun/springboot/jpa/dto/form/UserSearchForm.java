package com.niuchaoqun.springboot.jpa.dto.form;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserSearchForm {
    private String username;

    @Pattern(regexp = "(male|female)")
    private String sex;
}