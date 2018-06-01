package com.niuchaoqun.jpa.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Pattern;

@Data
public class UserEditForm {
    @Length(min = 2, max = 32)
    private String name;

    @Length(min = 6, max = 32, message = "长度不符合要求")
    private String password;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String birthday;

    @Pattern(regexp = "(male|female)")
    private String sex;

    @Range(min = 0, max = 1)
    private Integer state;

    private String job;

    private String address;
}
