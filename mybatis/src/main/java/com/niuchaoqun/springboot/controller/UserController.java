package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;

import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            Optional<User> user = userMapper.findById(userId);

            return Response.data(user.orElse(null));
        }

        return Response.error("参数错误");
    }

}
