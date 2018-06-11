package com.niuchaoqun.springboot.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niuchaoqun.springboot.dto.form.UserSearchForm;
import com.niuchaoqun.springboot.entity.Role;
import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.entity.UserDetail;
import com.niuchaoqun.springboot.entity.UserProfile;
import com.niuchaoqun.springboot.dto.form.UserAddForm;
import com.niuchaoqun.springboot.dto.form.UserEditForm;
import com.niuchaoqun.springboot.mapper.RoleMapper;
import com.niuchaoqun.springboot.mapper.UserDetailMapper;
import com.niuchaoqun.springboot.mapper.UserMapper;
import com.niuchaoqun.springboot.mapper.UserProfileMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDetailMapper userDetailMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    @Autowired
    RoleMapper roleMapper;

    @Transactional
    public User add(UserAddForm userAdd) throws ParseException, java.text.ParseException {

        // 检查用户名重复
        User existUser = new User();
        existUser.setUsername(userAdd.getUsername());
        User exist = userMapper.selectOne(existUser);
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 校验角色参数
        short role_id = userAdd.getRole_id();
        Role role = roleMapper.selectByPrimaryKey(role_id);
        if (role == null) {
            throw new RuntimeException("角色ID不存在");
        }

        // 加盐
        String password = userAdd.getPassword();
        String salt = RandomStringUtils.random(6, true, true).toLowerCase();
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        password = DigestUtils.md5DigestAsHex((md5 + salt).getBytes());

        User user = new User();
        user.setName(userAdd.getName());
        user.setSex(userAdd.getSex());
        user.setUsername(userAdd.getUsername());
        user.setPassword(password);
        user.setSalt(salt);
        user.setRoleId(role_id);
        user.setState(1);

        // 格式化日期参数
        if (userAdd.getBirthday() != null) {
            user.setBirthday(LocalDate.parse(userAdd.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            user.setBirthday(null);
        }
        if (userAdd.getAccess() != null) {
            user.setAccess(LocalDateTime.parse(userAdd.getAccess(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            user.setAccess(null);
        }
        if (userAdd.getAccess_time() != null) {
            user.setAccessTime(LocalTime.parse(userAdd.getAccess_time(), DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            user.setAccessTime(null);
        }

        // 写入 user
        int rows = userMapper.insertSelective(user);

        if (rows > 0) {
            // 写入 detail
            UserDetail userDetail = new UserDetail();
            userDetail.setUserId(user.getId());
            if (userAdd.getAddress() != null) {
                userDetail.setAddress(userAdd.getAddress());
            }
            userDetailMapper.insertSelective(userDetail);

            // profile 需要拿到 userid 才能写入
            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(user.getId());
            if (userAdd.getJob() != null) {
                userProfile.setJob(userAdd.getJob());
            }
            userProfileMapper.insertSelective(userProfile);

            return userMapper.selectByPrimaryKey(user.getId());
        } else {
            return null;
        }
    }


    public User edit(Long id, UserEditForm userEdit) throws ParseException, java.text.ParseException {
        User editUser = userMapper.selectByPrimaryKey(id);
        if (editUser != null) {
            if (userEdit.getName() != null) {
                editUser.setName(userEdit.getName());
            }
            if (userEdit.getPassword() != null) {
                String password = userEdit.getPassword();
                String salt = RandomStringUtils.random(6, true, true).toLowerCase();
                String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
                password = DigestUtils.md5DigestAsHex((md5 + salt).getBytes());

                editUser.setPassword(password);
                editUser.setSalt(salt);
            }
            if (userEdit.getBirthday() != null) {
                editUser.setBirthday(LocalDate.parse(userEdit.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (userEdit.getSex() != null) {
                editUser.setSex(userEdit.getSex());
            }
            if (userEdit.getState() != null) {
                editUser.setState(userEdit.getState());
            }

            int rows = userMapper.updateByPrimaryKeySelective(editUser);

            if (rows > 0) {
                return editUser;
            } else {
                throw new RuntimeException("操作失败");
            }

        } else {
            throw new RuntimeException("用户不存在");
        }
    }

    public List<User> search(UserSearchForm userSearch) {
        Example example = new Example(User.class);
        example.createCriteria();

        if (userSearch.getRole_id() != null) {
            example.and().andEqualTo("roleId", userSearch.getRole_id());
        }
        if (userSearch.getSex() != null) {
            example.and().andEqualTo("sex", userSearch.getSex());
        }
        example.orderBy("created").desc();

        List<User> users = userMapper.selectByExample(example);
        return users;
    }
}
