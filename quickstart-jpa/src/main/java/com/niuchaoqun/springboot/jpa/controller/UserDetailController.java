package com.niuchaoqun.springboot.jpa.controller;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.jpa.entity.UserDetail;
import com.niuchaoqun.springboot.jpa.repository.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_detail")
@Slf4j
public class UserDetailController extends BaseController {
    @Autowired
    private UserDetailRepository userDetailRepository;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            UserDetail userDetail = userDetailRepository.findByUserId(userId);
            if (userDetail != null) {
                return RestResponse.data(userDetail);
            }
        }

        return RestResponse.fail("参数错误");
    }
}
