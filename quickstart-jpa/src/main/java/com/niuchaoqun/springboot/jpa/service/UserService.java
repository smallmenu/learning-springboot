package com.niuchaoqun.springboot.jpa.service;

import com.niuchaoqun.springboot.jpa.dto.form.UserAddForm;
import com.niuchaoqun.springboot.jpa.dto.form.UserEditForm;
import com.niuchaoqun.springboot.jpa.entity.Role;
import com.niuchaoqun.springboot.jpa.entity.User;
import com.niuchaoqun.springboot.jpa.entity.UserDetail;
import com.niuchaoqun.springboot.jpa.entity.UserProfile;
import com.niuchaoqun.springboot.jpa.repository.RoleRepository;
import com.niuchaoqun.springboot.jpa.repository.UserDetailRepository;
import com.niuchaoqun.springboot.jpa.repository.UserProfileRepository;
import com.niuchaoqun.springboot.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public User add(UserAddForm userAdd) throws ParseException, java.text.ParseException {

        // 检查用户名重复
        User exist = userRepository.findByUsername(userAdd.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 校验角色参数
        short role_id = userAdd.getRole_id();
        Optional<Role> role = roleRepository.findById(role_id);
        if (!role.isPresent()) {
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
        user.setRole(role.get());
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
        user = userRepository.saveAndFlush(user);

        // 写入 detail
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);
        if (userAdd.getAddress() != null) {
            userDetail.setAddress(userAdd.getAddress());
        }
        userDetailRepository.saveAndFlush(userDetail);

        // profile 需要拿到 userid 才能写入
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        if (userAdd.getJob() != null) {
            userProfile.setJob(userAdd.getJob());
        }
        userProfileRepository.saveAndFlush(userProfile);

        // 需要手动回写数据
        user.setDetail(userDetail);
        user.setProfile(userProfile);

        return user;
    }


    public User edit(Long id, UserEditForm userEdit) throws ParseException, java.text.ParseException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            User editUser = user.get();

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

            userRepository.save(editUser);

            return editUser;
        } else {
            throw new RuntimeException("用户不存在");
        }
    }

}
