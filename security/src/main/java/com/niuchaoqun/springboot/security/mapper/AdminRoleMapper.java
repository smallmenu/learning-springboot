package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.entity.AdminRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface AdminRoleMapper extends Mapper<AdminRole>, MySqlMapper<AdminRole> {
}
