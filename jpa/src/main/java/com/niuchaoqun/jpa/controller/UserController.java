package com.niuchaoqun.jpa.controller;

import com.niuchaoqun.jpa.core.BaseController;
import com.niuchaoqun.jpa.dto.UserAddDto;
import com.niuchaoqun.jpa.dto.UserEditDto;
import com.niuchaoqun.jpa.dto.UserSearchDto;
import com.niuchaoqun.jpa.entity.Order;
import com.niuchaoqun.jpa.entity.Role;
import com.niuchaoqun.jpa.entity.User;
import com.niuchaoqun.jpa.entity.UserDetail;
import com.niuchaoqun.jpa.repository.UserDetailRepository;
import com.niuchaoqun.jpa.repository.UserRepository;
import com.niuchaoqun.jpa.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@Valid UserAddDto userAdd, BindingResult result) {
        if (result.hasErrors()) {
            return this.responseError(this.resultErrors(result));
        }

        try {
            User newUser = userService.add(userAdd);
            return this.responseData(newUser);
        } catch (Exception e) {
            return this.responseError(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object page(@RequestParam(name = "page", required = false) Integer page,
                       @RequestParam(name="size", required = false) Integer size) {
        page = page == null ? 0 : Math.max(0, page);
        size = size == null ? 10 : Math.max(10, size);

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);

        Page<User> users = userRepository.findAll(pageable);

        return this.responseData(users);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Object search(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         UserSearchDto userSearch) {
        logger.info(userSearch.toString());
        Page<User> users = userRepository.search(userSearch, pageable);

        return this.responseData(users);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userService.findOne(userId);
            if (user != null) {
                return this.responseData(user);
            }
        }

        return this.responseError("参数错误");
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Object getUsername(@RequestParam String email) {
        if (!StringUtils.isEmpty(email)) {
            User user = userRepository.findByUsername(email);
            return this.responseData(user);
        }

        return this.responseError("参数错误");
    }

    @RequestMapping(value = "/{userId}/state/{state}", method = RequestMethod.PUT)
    public Object updateState(@PathVariable Long userId,
                              @PathVariable(value = "state", required = true) Integer state) {
        if (userRepository.saveState(userId, state) > 0) {
            return this.responseSuccess("操作成功");
        }
        return this.responseError("操作失败");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Object edit(@PathVariable Long userId,
                       @Valid UserEditDto userEdit, BindingResult result) {
        logger.info(userEdit.toString());
        if (result.hasErrors()) {
            return this.responseError(this.resultErrors(result));
        }

        if (userId > 0) {
            try {
                User edit = userService.edit(userId, userEdit);
                return this.responseData(edit);
            } catch (Exception e) {
                return this.responseError(e.getLocalizedMessage());
            }
        }

        return this.responseError("参数错误");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long userId) {
        if (userId > 0) {
            boolean exists = userRepository.exists(userId);
            if (exists) {
                userRepository.delete(userId);
                return this.responseSuccess("操作成功");
            } else {
                return this.responseError("用户不存在");
            }
        }
        return this.responseError("参数错误");
    }

    @RequestMapping(value = "/orders/{userId}", method = RequestMethod.GET)
    public Object orders(@PathVariable Long userId) {
        if (userId > 0) {
            User user = userService.findOne(userId);
            if (user != null) {
                List<Order> orders = user.getOrders();
                return this.responseData(orders);
            }
        }

        return this.responseError("参数错误");
    }
}
