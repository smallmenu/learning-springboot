package com.niuchaoqun.springboot.jpa.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.jpa.entity.Role;
import com.niuchaoqun.springboot.jpa.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public RestResult get(@PathVariable Short roleId) {
        if (roleId > 0) {
            Optional<Role> role = roleRepository.findById(roleId);
            return RestResponse.data(role.orElse(null));
        }

        return RestResponse.fail("参数错误");
    }
}
