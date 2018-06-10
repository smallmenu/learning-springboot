package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;
import com.niuchaoqun.springboot.entity.UserDetail;
import com.niuchaoqun.springboot.mapper.UserDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_detail")
public class UserDetailController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailController.class);

    @Autowired
    private UserDetailMapper userDetailMapper;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            UserDetail select = new UserDetail();
            select.setUserId(userId);
            UserDetail userDetail = userDetailMapper.selectOne(select);
            if (userDetail != null) {
                return Response.data(userDetail);
            }
        }

        return Response.error("参数错误");
    }
}
