package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
    User selectUserRelationByPrimaryKey(Long id);
}
