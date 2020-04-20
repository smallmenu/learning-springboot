package com.niuchaoqun.springboot.security.service.impl;

import com.niuchaoqun.springboot.security.dto.form.AdminAddForm;
import com.niuchaoqun.springboot.security.dto.form.AdminEditForm;
import com.niuchaoqun.springboot.security.dto.form.AdminSearchForm;
import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.entity.AdminRole;
import com.niuchaoqun.springboot.security.entity.Role;
import com.niuchaoqun.springboot.security.jwt.JwtUser;
import com.niuchaoqun.springboot.security.mapper.AdminMapper;
import com.niuchaoqun.springboot.security.mapper.AdminRoleMapper;
import com.niuchaoqun.springboot.security.mapper.RoleMapper;
import com.niuchaoqun.springboot.security.service.AdminService;
import com.niuchaoqun.springboot.security.service.AuthService;
import com.niuchaoqun.springboot.security.service.RoleService;
import com.niuchaoqun.springboot.security.util.DbUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public Admin get(Long id) {
        return exist(id);
    }

    @Override
    public Admin exist(Long id) {
        return Optional.ofNullable(adminMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new EntityNotFoundException("ID 不存在"));
    }

    @Override
    public List<Admin> search(AdminSearchForm searchForm) {
        return adminMapper.search(searchForm);
    }

    @Override
    public Admin getRelationById(Long id) {
        exist(id);

        return adminMapper.getRelationById(id);
    }

    @Override
    public Admin getRelationByUsername(String username) {
        Admin admin = existAdminUsername(username);

        return adminMapper.getRelationById(admin.getId());
    }

    @Override
    public Admin getByUsername(String username) {
        return existAdminUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Admin edit(Long id, AdminEditForm editForm) {
        exist(id);

        Admin editAdmin = Admin.builder().id(id).build();

        // 更新密码
        if (editForm.getPassword() != null) {
            editAdmin.setPassword(bCryptPasswordEncoder.encode(editForm.getPassword()));
        }

        // 其他字段更新
        Optional.ofNullable(editForm.getName()).ifPresent(editAdmin::setName);
        Optional.ofNullable(editForm.getEmail()).ifPresent(editAdmin::setEmail);
        Optional.ofNullable(editForm.getState()).ifPresent(editAdmin::setState);

        // 获取当前用户
        JwtUser jwtUser = authService.logined();
        editAdmin.setUpdatedby(jwtUser.getId());

        adminMapper.updateByPrimaryKeySelective(editAdmin);

        // 更新角色
        if (editForm.getRoleId() != null) {
            // 清空角色关联信息
            adminRoleMapper.delete(AdminRole.builder().adminid(id).build());

            // 更新角色
            updateRoles(id, editForm.getRoleId());
        }

        return adminMapper.getRelationById(editAdmin.getId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        exist(id);

        int rows = adminMapper.deleteByPrimaryKey(id);

        if (rows > 0) {
            adminRoleMapper.delete(AdminRole.builder().adminid(id).build());
            return true;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Admin add(AdminAddForm addForm) {
        Admin adminQuery = Admin.builder().username(addForm.getUsername()).build();

        Optional.ofNullable(adminMapper.selectOne(adminQuery))
                .ifPresent(o -> {
                    throw new EntityExistsException("用户名已存在");
                });

        JwtUser jwtUser = authService.logined();

        Admin admin = Admin.builder()
                .username(addForm.getUsername())
                .name(addForm.getName())
                .email(addForm.getEmail())
                .createdby(jwtUser.getId())
                .build();

        // 生成密码
        admin.setPassword(bCryptPasswordEncoder.encode(addForm.getPassword()));

        // 插入管理员
        adminMapper.insertSelective(admin);

        // 更新角色
        updateRoles(admin.getId(), addForm.getRoleId());

        return adminMapper.getRelationById(admin.getId());
    }

    private void updateRoles(Long adminid, String roleidString) {
        List<AdminRole> adminRoles = new ArrayList<>();
        List<String> roleidStrings = DbUtil.idStringToList(roleidString);
        if (!roleidStrings.isEmpty()) {
            for (String roleidStr : roleidStrings) {
                long roleid = NumberUtils.toLong(roleidStr);

                try {
                    Role existRole = roleService.exist(roleid);

                    AdminRole adminRole = AdminRole.builder().adminid(adminid).roleid(existRole.getId()).build();
                    adminRoles.add(adminRole);
                } catch (EntityNotFoundException e) {
                    log.warn("非法角色 {roleid}", roleid);
                }
            }
        }

        // 插入关联表
        if (!adminRoles.isEmpty()) {
            adminRoleMapper.insertList(adminRoles);
        } else {
            throw new EntityNotFoundException("角色不存在");
        }
    }

    private Admin existAdminUsername(String username) {
        Admin adminQuery = Admin.builder().username(username).build();
        Admin admin = adminMapper.selectOne(adminQuery);

        return Optional.ofNullable(admin)
                .orElseThrow(() -> new EntityNotFoundException("用户名不存在"));
    }
}
