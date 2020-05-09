package com.niuchaoqun.springboot.security.service;

import com.niuchaoqun.springboot.security.dto.form.AdminAddForm;
import com.niuchaoqun.springboot.security.dto.form.AdminEditForm;
import com.niuchaoqun.springboot.security.dto.form.AdminSearchForm;
import com.niuchaoqun.springboot.security.entity.Admin;

import java.util.List;


public interface AdminService {

    Admin get(Long id);

    Admin exist(Long id);

    Admin edit(Long id, AdminEditForm editForm);

    boolean remove(Long id);

    Admin add(AdminAddForm addForm);

    /**
     * 根据用户名获取单表数据
     *
     * @param username
     * @return
     */
    Admin getByUsername(String username);

    /**
     * 根据ID获取关联数据
     *
     * @param id
     * @return
     */
    Admin getRelationById(Long id);

    /**
     * 根据用户名获取关联数据
     *
     * @param username
     * @return
     */
    Admin getRelationByUsername(String username);

    /**
     * 搜索
     *
     * @param searchForm
     * @return
     */
    List<Admin> search(AdminSearchForm searchForm);
}
