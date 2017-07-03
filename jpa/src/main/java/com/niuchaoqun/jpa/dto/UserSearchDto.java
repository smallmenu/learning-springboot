package com.niuchaoqun.jpa.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserSearchDto {
    private String username;

    @Pattern(regexp = "(male|female)")
    private String sex;
}