package com.niuchaoqun.springboot.mapper;

import com.niuchaoqun.springboot.entity.UserProfile;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserProfileMapper extends Mapper<UserProfile> {
}