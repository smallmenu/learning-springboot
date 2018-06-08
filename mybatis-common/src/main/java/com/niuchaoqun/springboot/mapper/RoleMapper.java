package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleMapper extends Mapper<Role> {
}