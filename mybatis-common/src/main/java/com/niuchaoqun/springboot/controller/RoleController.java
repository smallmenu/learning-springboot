package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.Role;
import com.niuchaoqun.springboot.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Object get(@PathVariable Short roleId) {
        if (roleId > 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role != null) {
                return Response.data(role);
            } else {
                return Response.error("ID不存在");
            }

        }

        return Response.error("参数错误");
    }
}
