package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.entity.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PermissionMapper extends Mapper<Permission> {
    List<Permission> selectByRoleIds(List<Long> roleIds);
}
