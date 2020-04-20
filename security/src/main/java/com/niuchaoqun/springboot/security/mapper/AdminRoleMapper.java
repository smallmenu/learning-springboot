package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.entity.AdminRole;
import com.niuchaoqun.springboot.security.entity.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Repository
public interface AdminRoleMapper extends Mapper<AdminRole>, MySqlMapper<AdminRole> {
    List<Role> selectRolesByAdminId(Long adminId);

    List<Admin> selectAdminsByRoleId(Long roleId);
}
