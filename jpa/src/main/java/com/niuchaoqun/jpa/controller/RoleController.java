package com.niuchaoqun.jpa.controller;

import com.niuchaoqun.jpa.core.BaseController;
import com.niuchaoqun.jpa.entity.Role;
import com.niuchaoqun.jpa.entity.User;
import com.niuchaoqun.jpa.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleRepository roleRepository;

//    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
//    public Object get(@PathVariable Short roleId) {
//        if (roleId > 0) {
//            Role role = roleRepository.findOne(roleId);
//            if (role != null) {
//                return this.responseData(role);
//            }
//        }
//
//        return this.responseError("参数错误");
//    }
//
//    @RequestMapping(value = "/users/{roleId}", method = RequestMethod.GET)
//    public Object users(@PathVariable Short roleId) {
//        if (roleId > 0) {
//            Role role = roleRepository.findOne(roleId);
//            if (role != null) {
//                List<User> users = role.getUsers();
//                return this.responseData(users);
//            }
//        }
//
//        return this.responseError("参数错误");
//    }
}
