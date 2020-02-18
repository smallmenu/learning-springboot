package com.niuchaoqun.springboot.jpa.controller;

import com.niuchaoqun.springboot.jpa.core.BaseController;
import com.niuchaoqun.springboot.jpa.core.Response;
import com.niuchaoqun.springboot.jpa.entity.Role;
import com.niuchaoqun.springboot.jpa.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Object get(@PathVariable Short roleId) {
        if (roleId > 0) {
            Optional<Role> role = roleRepository.findById(roleId);
            return Response.data(role.orElse(null));
        }

        return Response.error("参数错误");
    }
}
