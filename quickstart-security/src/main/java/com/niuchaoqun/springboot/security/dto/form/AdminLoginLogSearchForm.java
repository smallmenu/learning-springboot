package com.niuchaoqun.springboot.security.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;


@Data
public class AdminLoginLogSearchForm {

    @ApiModelProperty(value = "状态", required = false, dataType = "Integer")
    @PositiveOrZero
    private Integer state;

    @ApiModelProperty(value = "管理员用户名", required = false, dataType = "String")
    private String username;

    @ApiModelProperty(value = "IP", required = false, dataType = "String")
    private String loginIp;

    @ApiModelProperty(value = "排序字段(id|logined)，默认为id", required = false, dataType = "String")
    @Pattern(regexp = "(id|logined)", message = "排序字段不合法")
    private String orderby;

    @ApiModelProperty(value = "排序(desc|asc)，默认为desc", required = false, dataType = "String")
    @Pattern(regexp = "(desc|asc)", message = "排序不合法")
    private String order;

    @ApiModelProperty(value = "分页页码，默认第1页", required = false, dataType = "String")
    private Integer page;

    @ApiModelProperty(value = "分页数量，默认15条", required = false, dataType = "String")
    private Integer size;
}
