package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.entity.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleMapper extends Mapper<Role> {
    List<Role> selectByAdminId(Long adminId);
}
