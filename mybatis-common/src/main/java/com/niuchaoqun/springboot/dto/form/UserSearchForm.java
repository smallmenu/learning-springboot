package com.niuchaoqun.springboot.dto.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserSearchForm {
    @NotNull
    private Short role_id;

    @Pattern(regexp = "(male|female)")
    private String sex;

    private Integer page;

    private Integer size;
}