package com.niuchaoqun.springboot.mybatis.common.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.mybatis.common.entity.Role;
import com.niuchaoqun.springboot.mybatis.common.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {
    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public RestResult get(@PathVariable Short roleId) {
        if (roleId > 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role != null) {
                return RestResponse.data(role);
            } else {
                return RestResponse.fail("ID不存在");
            }
        }

        return RestResponse.fail("参数错误");
    }
}
