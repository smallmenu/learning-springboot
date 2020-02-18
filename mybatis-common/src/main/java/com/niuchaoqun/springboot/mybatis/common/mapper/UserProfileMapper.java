package com.niuchaoqun.springboot.mybatis.common.mapper;

import com.niuchaoqun.springboot.mybatis.common.entity.UserProfile;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserProfileMapper extends Mapper<UserProfile> {
}