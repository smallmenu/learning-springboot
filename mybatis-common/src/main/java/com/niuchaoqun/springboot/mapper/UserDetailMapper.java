package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.UserDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserDetailMapper extends Mapper<UserDetail> {
}

