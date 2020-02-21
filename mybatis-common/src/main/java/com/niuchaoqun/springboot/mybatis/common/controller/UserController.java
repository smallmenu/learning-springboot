package com.niuchaoqun.springboot.mybatis.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.mybatis.common.dto.form.UserAddForm;
import com.niuchaoqun.springboot.mybatis.common.dto.form.UserEditForm;
import com.niuchaoqun.springboot.mybatis.common.dto.form.UserSearchForm;
import com.niuchaoqun.springboot.mybatis.common.entity.User;
import com.niuchaoqun.springboot.mybatis.common.mapper.UserMapper;
import com.niuchaoqun.springboot.mybatis.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestResult add(@Valid UserAddForm userAdd, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        try {
            User user = userService.add(userAdd);
            return RestResponse.data(user);
        } catch (Exception e) {
            return RestResponse.fail(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RestResult page(@RequestParam(name = "page", required = false) Integer page,
                           @RequestParam(name = "size", required = false) Integer size) {
        page = page == null ? 0 : Math.max(0, page);
        size = size == null ? 10 : Math.max(3, size);

        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        PageInfo pageUsers = new PageInfo(users);

        return RestResponse.data(pageUsers);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public RestResult search(@Valid UserSearchForm userSearchForm, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        Integer page = userSearchForm.getPage();
        Integer size = userSearchForm.getSize();
        page = page == null ? 0 : Math.max(0, page);
        size = size == null ? 10 : Math.max(3, size);

        PageHelper.startPage(page, size);
        List<User> users = userService.search(userSearchForm);
        PageInfo pageUsers = new PageInfo(users);
        return RestResponse.data(pageUsers);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public RestResult get(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            return RestResponse.data(user);
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/complete/{userId}", method = RequestMethod.GET)
    public RestResult complete(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userMapper.selectUserRelationByPrimaryKey(userId);
            return RestResponse.data(user);
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public RestResult getUsername(@RequestParam String email) {
        if (!StringUtils.isEmpty(email)) {
            User selectUser = new User();
            selectUser.setUsername(email);
            User user = userMapper.selectOne(selectUser);
            return RestResponse.data(user);
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/{userId}/state/{state}", method = RequestMethod.PUT)
    public RestResult updateState(@PathVariable Long userId,
                                  @PathVariable(value = "state", required = true) Integer state) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                user.setState(state);
                int action = userMapper.updateByPrimaryKeySelective(user);
                if (action == 1) {
                    return RestResponse.success("操作成功");
                }
            }
        }
        return RestResponse.success("操作失败");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public RestResult edit(@PathVariable Long userId,
                           @Valid UserEditForm userEdit, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        if (userId > 0) {
            try {
                User edit = userService.edit(userId, userEdit);
                return RestResponse.data(edit);
            } catch (Exception e) {
                return RestResponse.fail(e.getLocalizedMessage());
            }
        }

        return RestResponse.fail("参数错误");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public RestResult delete(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                userMapper.deleteByPrimaryKey(userId);
                return RestResponse.success("操作成功");
            } else {
                return RestResponse.fail("用户不存在");
            }
        }
        return RestResponse.fail("参数错误");
    }
}
