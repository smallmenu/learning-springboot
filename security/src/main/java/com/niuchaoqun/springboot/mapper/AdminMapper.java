package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminMapper extends Mapper<Admin> {
    Admin getRelationById(Long id);

    Admin getRelationByUsername(String username);
}