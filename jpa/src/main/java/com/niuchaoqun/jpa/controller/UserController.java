package com.niuchaoqun.jpa.controller;

import com.niuchaoqun.jpa.core.BaseController;
import com.niuchaoqun.jpa.core.Response;
import com.niuchaoqun.jpa.dto.form.UserAddForm;
import com.niuchaoqun.jpa.dto.form.UserEditForm;
import com.niuchaoqun.jpa.entity.Order;
import com.niuchaoqun.jpa.entity.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@Valid UserAddForm userAdd, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(this.resultErrors(result));
        }

        try {
            User user = userService.add(userAdd);
            Optional<User> newUser = userRepository.findById(user.getId());
            return Response.data(newUser.orElse(null));
        } catch (Exception e) {
            return Response.error(e.getLocalizedMessage());
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

        return Response.data(users);
    }

//    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    public Object search(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//                         UserSearchForm userSearch) {
//        logger.info(userSearch.toString());
//        Page<User> users = userRepository.search(userSearch, pageable);
//
//        return this.responseData(users);
//    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object get(@PathVariable Long userId) {
        if (userId > 0) {
            Optional<User> user = userRepository.findById(userId);

            logger.info(user.get().toString());
            return Response.data(user.orElse(null));
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/lazy/{userId}", method = RequestMethod.GET)
    public Object getLazy(@PathVariable Long userId) {
        if (userId > 0) {
            Optional<User> user = userRepository.findById(userId);
            return Response.data(user.orElse(null));
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Object getUsername(@RequestParam String email) {
        if (!StringUtils.isEmpty(email)) {
            User user = userRepository.findByUsername(email);
            return Response.data(user);
        }

        return Response.error("参数错误");
    }

    @RequestMapping(value = "/{userId}/state/{state}", method = RequestMethod.PUT)
    public Object updateState(@PathVariable Long userId,
                              @PathVariable(value = "state", required = true) Integer state) {
        if (userRepository.saveState(userId, state) > 0) {
            return Response.success("操作成功");
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
            boolean exists = userRepository.existsById(userId);
            if (exists) {
                userRepository.deleteById(userId);
                return Response.success("操作成功");
            } else {
                return Response.error("用户不存在");
            }
        }
        return Response.error("参数错误");
    }

    @RequestMapping(value = "/orders/{userId}", method = RequestMethod.GET)
    public Object orders(@PathVariable Long userId) {
        if (userId > 0) {
            Optional<User> user = userRepository.findById(userId);

            return Response.data(user.orElse(null));
        }

        return Response.error("参数错误");
    }
}
