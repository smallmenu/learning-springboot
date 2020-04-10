package com.niuchaoqun.springboot.security.service.impl;

import com.niuchaoqun.springboot.security.entity.Role;
import com.niuchaoqun.springboot.security.mapper.RoleMapper;
import com.niuchaoqun.springboot.security.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role get(Long id) {
        return exist(id);
    }

    @Override
    public Role exist(Long id) {
        return Optional.ofNullable(roleMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new EntityNotFoundException("role id not exist"));
    }
}
