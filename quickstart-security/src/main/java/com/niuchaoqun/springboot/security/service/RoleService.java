package com.niuchaoqun.springboot.security.service;

import com.niuchaoqun.springboot.security.entity.Role;


public interface RoleService {

    Role get(Long id);

    Role exist(Long id);
}
