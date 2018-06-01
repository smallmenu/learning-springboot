package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findById(@Param("id") Long id);
}
