package com.niuchaoqun.springboot.mybatis.common.mapper;

import com.niuchaoqun.springboot.mybatis.common.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
    User selectUserRelationByPrimaryKey(Long id);
}
