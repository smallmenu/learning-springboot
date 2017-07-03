package com.niuchaoqun.jpa.controller;

import com.niuchaoqun.jpa.core.BaseController;
import com.niuchaoqun.jpa.entity.User;
import com.niuchaoqun.jpa.entity.UserDetail;
import com.niuchaoqun.jpa.repository.UserDetailRepository;
import com.niuchaoqun.jpa.repository.UserRepository;
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
    private UserDetailRepository userDetailRepository;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            UserDetail userDetail = userDetailRepository.findByUserId(userId);
            //if (userDetail != null) {
                User user = userDetail.getUser();
                return this.responseData(user);
            //}
        }

        return this.responseError("参数错误");
    }
}
