package com.niuchaoqun.jpa.service;

import com.niuchaoqun.jpa.dto.UserAddDto;
import com.niuchaoqun.jpa.dto.UserEditDto;
import com.niuchaoqun.jpa.entity.Role;
import com.niuchaoqun.jpa.entity.User;
import com.niuchaoqun.jpa.entity.UserDetail;
import com.niuchaoqun.jpa.repository.RoleRepository;
import com.niuchaoqun.jpa.repository.UserDetailRepository;
import com.niuchaoqun.jpa.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import javax.xml.crypto.dsig.DigestMethod;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    RoleRepository roleRepository;

//    @Transactional
//    public User findOne(Long userId) {
//        return userRepository.findOne(userId);
//    }
//
//    @Transactional
//    public User add(UserAddDto userAdd) throws ParseException {
//
//        // 检查用户名重复
//        User exist = userRepository.findByUsername(userAdd.getUsername());
//        if (exist != null) {
//            throw new RuntimeException("用户名已存在");
//        }
//
//        // 校验角色参数
//        short role_id = Short.parseShort(userAdd.getRole_id());
//        Role role = roleRepository.findOne(role_id);
//        if (role == null) {
//            throw new RuntimeException("角色未定义");
//        }
//
//        // 加盐
//        HashMap<String, String> passwords = password(userAdd.getPassword());
//
//        User user = new User();
//        user.setName(userAdd.getName());
//        user.setUsername(userAdd.getUsername());
//        user.setPassword(passwords.get("p"));
//        user.setSalt(passwords.get("s"));
//        user.setRole(role);
//
//        // 格式化日期参数
//        if (userAdd.getBirthday() != null) {
//            user.setBirthday(new Date(DateUtils.parseDate(userAdd.getBirthday(), "yyyy-MM-dd").getTime()));
//        } else {
//            user.setBirthday(null);
//        }
//        if (userAdd.getAccess() != null) {
//            user.setAccess(new Timestamp(DateUtils.parseDate(userAdd.getAccess(), "yyyy-MM-dd HH:mm:ss").getTime()));
//        } else {
//            user.setAccess(null);
//        }
//        if (userAdd.getAccess_time() != null) {
//            user.setAccessTime(new Time(DateUtils.parseDate(userAdd.getAccess_time(), "HH:mm:ss").getTime()));
//        } else {
//            user.setAccessTime(null);
//        }
//
//        // $1 手动插入
//        user = userRepository.saveAndFlush(user);
//
//        UserDetail userDetail = new UserDetail();
//        userDetail.setUser(user);
//        if (userAdd.getAddress() != null) {
//            userDetail.setAddress(userAdd.getAddress());
//        }
//        userDetailRepository.saveAndFlush(userDetail);
//
//
////        UserDetail userDetail = new UserDetail();
////        if (userAdd.getAddress() != null) {
////            userDetail.setAddress(userAdd.getAddress());
////        }
////        user.setDetail(userDetail);
////        userRepository.saveAndFlush(user);
////
////        userDetail.setUser(user);
////        userDetailRepository.saveAndFlush(userDetail);
//
//        return user;
//    }
//
//    public User edit(Long id, UserEditDto userEdit) throws ParseException {
//        User user = userRepository.findOne(id);
//        if (user != null) {
//            if (userEdit.getName() != null) {
//                user.setName(userEdit.getName());
//            }
//            if (userEdit.getPassword() != null) {
//                HashMap<String, String> passwords = password(userEdit.getPassword());
//                user.setPassword(passwords.get("p"));
//                user.setSalt(passwords.get("s"));
//            }
//            if (userEdit.getBirthday() != null) {
//                user.setBirthday(new Date(DateUtils.parseDate(userEdit.getBirthday(), "yyyy-MM-dd").getTime()));
//            }
//            if (userEdit.getSex() != null) {
//                user.setSex(userEdit.getSex());
//            }
//            if (userEdit.getState() != null) {
//                user.setState(userEdit.getState());
//            }
//
//            logger.info(user.toString());
//
//            userRepository.save(user);
//            return user;
//        } else {
//            throw new RuntimeException("用户不存在");
//        }
//    }
//
//    private HashMap<String, String> password(String p) {
//        HashMap<String, String> passwords = new HashMap<String, String>();
//        String salt = RandomStringUtils.random(6, true, true).toLowerCase();
//        String md5 = DigestUtils.md5DigestAsHex(p.getBytes());
//        String password = DigestUtils.md5DigestAsHex((md5 + salt).getBytes());
//
//        passwords.put("p", password);
//        passwords.put("s", salt);
//
//        return passwords;
//    }
}
