package com.niuchaoqun.springboot.security.mapper;

import com.niuchaoqun.springboot.security.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminMapper extends Mapper<Admin> {
}