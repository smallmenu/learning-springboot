package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface RoleMapper {
    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findById(Short id);
}
