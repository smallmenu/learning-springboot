package com.niuchaoqun.springboot.security.service;

import com.niuchaoqun.springboot.security.entity.Role;

import java.util.List;

/**
 * @author niuchaoqun
 */
public interface RoleService {

    Role get(Long id);

    Role exist(Long id);

    List<Role> allByCache();
}
