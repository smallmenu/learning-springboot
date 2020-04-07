package com.niuchaoqun.springboot.security.service.impl;

import com.niuchaoqun.springboot.security.common.Core;
import com.niuchaoqun.springboot.security.dto.form.AdminAddForm;
import com.niuchaoqun.springboot.security.dto.form.AdminEditForm;
import com.niuchaoqun.springboot.security.dto.form.AdminSearchForm;
import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.entity.Role;
import com.niuchaoqun.springboot.security.jwt.JwtUser;
import com.niuchaoqun.springboot.security.mapper.AdminMapper;
import com.niuchaoqun.springboot.security.mapper.RoleMapper;
import com.niuchaoqun.springboot.security.service.AdminService;
import com.niuchaoqun.springboot.security.service.LoginService;
import com.niuchaoqun.springboot.security.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private Core core;

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
        Admin admin = checkExistAdminUsername(username);

        return adminMapper.getRelationById(admin.getId());
    }

    @Override
    public Admin getByUsername(String username) {
        return checkExistAdminUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Admin edit(Long id, AdminEditForm editForm) {
        Admin admin = exist(id);

        Admin editAdmin = Admin.builder().id(id).build();

        // 更新角色
        if (editForm.getRoleId() != null && admin.getRoleId().longValue() != editForm.getRoleId().longValue()) {
            Role oldRole = roleService.exist(admin.getRoleId());
            Role newRole = roleService.exist(editForm.getRoleId());

            // 原角色
            oldRole.setCount(oldRole.getCount() - 1);
            roleMapper.updateByPrimaryKeySelective(oldRole);
            core.tableCache(Role.class, oldRole.getId(), true);

            // 新角色
            newRole.setCount(newRole.getCount() + 1);
            roleMapper.updateByPrimaryKeySelective(newRole);
            core.tableCache(Role.class, newRole.getId(), true);

            // 重建缓存
            core.tableCache(Role.class, true);

            editAdmin.setRoleId(editForm.getRoleId());
        }

        // 更新密码
        if (editForm.getPassword() != null) {
            editAdmin.setPassword(bCryptPasswordEncoder.encode(editForm.getPassword()));
        }

        // 其他字段更新
        Optional.ofNullable(editForm.getName()).ifPresent(editAdmin::setName);
        Optional.ofNullable(editForm.getEmail()).ifPresent(editAdmin::setEmail);
        Optional.ofNullable(editForm.getState()).ifPresent(editAdmin::setState);

        // 获取当前用户
        JwtUser jwtUser = loginService.loginUser();
        editAdmin.setUpdatedby(jwtUser.getId());

        adminMapper.updateByPrimaryKeySelective(editAdmin);

        // 清空并重建缓存
        core.tableCache(Admin.class, editAdmin.getId(), true);

        return adminMapper.getRelationById(editAdmin.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        Admin admin = exist(id);

        int rows = adminMapper.deleteByPrimaryKey(id);

        if (rows > 0) {
            Long roleId = admin.getRoleId();
            Role role = roleService.exist(admin.getRoleId());
            role.setCount(role.getCount() - 1);

            boolean result = roleMapper.updateByPrimaryKeySelective(role) > 0;
            if (result) {
                // 重建缓存
                core.tableClear(Role.class, roleId);
                core.tableClear(Admin.class, id);

                core.tableCache(Role.class, true);
            }
            return result;
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

        JwtUser jwtUser = loginService.loginUser();
        Role existRole = roleService.exist(addForm.getRoleId());

        Admin admin = Admin.builder()
                .username(addForm.getUsername())
                .name(addForm.getName())
                .email(addForm.getEmail())
                .roleId(addForm.getRoleId())
                .createdby(jwtUser.getId())
                .build();

        // 生成密码
        admin.setPassword(bCryptPasswordEncoder.encode(addForm.getPassword()));

        // 插入
        adminMapper.insertSelective(admin);

        // 更新 Role
        existRole.setCount(existRole.getCount() + 1);
        roleMapper.updateByPrimaryKeySelective(existRole);

        // 重建缓存
        core.tableClear(Role.class, existRole.getId());
        core.tableClear(Admin.class, admin.getId());
        core.tableCache(Role.class, true);
        core.tableCache(Admin.class, admin.getId());

        return adminMapper.getRelationById(admin.getId());
    }

    private Admin checkExistAdminUsername(String username) {
        Admin adminQuery = Admin.builder().username(username).build();

        return Optional.ofNullable(adminMapper.selectOne(adminQuery))
                .orElseThrow(() -> new EntityNotFoundException("用户名不存在"));
    }
}
