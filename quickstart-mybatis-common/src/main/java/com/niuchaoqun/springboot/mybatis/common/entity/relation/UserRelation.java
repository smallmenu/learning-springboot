package com.niuchaoqun.springboot.mybatis.common.entity.relation;


import com.niuchaoqun.springboot.mybatis.common.entity.Role;
import com.niuchaoqun.springboot.mybatis.common.entity.User;
import com.niuchaoqun.springboot.mybatis.common.entity.UserDetail;
import com.niuchaoqun.springboot.mybatis.common.entity.UserProfile;
import lombok.Data;

@Data
public class UserRelation extends User {
    private Role role;

    private UserProfile profile;

    private UserDetail detail;
}
