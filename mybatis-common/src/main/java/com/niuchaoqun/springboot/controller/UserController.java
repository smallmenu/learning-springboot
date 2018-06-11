package com.niuchaoqun.springboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niuchaoqun.springboot.core.BaseController;
import com.niuchaoqun.springboot.core.Response;

import com.niuchaoqun.springboot.dto.form.UserSearchForm;
import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.dto.form.UserAddForm;
import com.niuchaoqun.springboot.dto.form.UserEditForm;
import com.niuchaoqun.springboot.mapper.UserMapper;

import com.niuchaoqun.springboot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@Valid UserAddForm userAdd, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(this.resultErrors(result));
        }

        try {
            User user = userService.add(userAdd);
            return Response.data(user);
        } catch (Exception e) {
            return Response.error(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object page(@RequestParam(name = "page", required = false) Integer page,
                       @RequestParam(name="size", required = false) Integer size) {
        page = page == null ? 0 : Math.max(0, page);
        size = size == null ? 10 : Math.max(3, size);

        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        PageInfo pageUsers = new PageInfo(users);

        return Response.data(pageUsers);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@Valid UserSearchForm userSearchForm, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(this.resultErrors(result));
        }

        Integer page = userSearchForm.getPage();
        Integer size = userSearchForm.getSize();
        page = page == null ? 0 : Math.max(0, page);
        size = size == null ? 10 : Math.max(3, size);

        PageHelper.startPage(page, size);
        List<User> users = userService.search(userSearchForm);
        PageInfo pageUsers = new PageInfo(users);
        return Response.data(pageUsers);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            return Response.data(user);
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Object getUsername(@RequestParam String email) {
        if (!StringUtils.isEmpty(email)) {
            User selectUser = new User();
            selectUser.setUsername(email);
            User user = userMapper.selectOne(selectUser);
            return Response.data(user);
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/{userId}/state/{state}", method = RequestMethod.PUT)
    public Object updateState(@PathVariable Long userId,
                              @PathVariable(value = "state", required = true) Integer state) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                user.setState(state);
                int action = userMapper.updateByPrimaryKeySelective(user);
                if (action == 1) {
                    return Response.success("操作成功");
                }
            }
        }
        return Response.success("操作失败");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Object edit(@PathVariable Long userId,
                       @Valid UserEditForm userEdit, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(this.resultErrors(result));
        }

        if (userId > 0) {
            try {
                User edit = userService.edit(userId, userEdit);
                return Response.data(edit);
            } catch (Exception e) {
                return Response.error(e.getLocalizedMessage());
            }
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                userMapper.deleteByPrimaryKey(userId);
                return Response.success("操作成功");
            } else {
                return Response.error("用户不存在");
            }
        }
        return Response.error("参数错误");
    }

//    @RequestMapping(value = "/orders/{userId}", method = RequestMethod.GET)
//    public Object orders(@PathVariable Long userId) {
//        if (userId > 0) {
//            Optional<User> user = userRepository.findById(userId);
//
//            return Response.data(user.orElse(null));
//        }
//
//        return Response.error("参数错误");
//    }
}
