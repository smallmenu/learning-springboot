package com.niuchaoqun.springboot.mybatis.common.mapper;

import com.niuchaoqun.springboot.mybatis.common.entity.UserDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserDetailMapper extends Mapper<UserDetail> {
}

